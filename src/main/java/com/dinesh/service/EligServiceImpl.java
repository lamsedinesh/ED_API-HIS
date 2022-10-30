package com.dinesh.service;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinesh.entity.CitizenAppEntity;
import com.dinesh.entity.CoTriggerEntity;
import com.dinesh.entity.DcCaseEntity;
import com.dinesh.entity.DcChildrenEntity;
import com.dinesh.entity.DcEducationEntity;
import com.dinesh.entity.DcIncomeEntity;
import com.dinesh.entity.EligDtlsEntity;
import com.dinesh.entity.PlanEntity;
import com.dinesh.repository.CitizenAppRepository;
import com.dinesh.repository.CoTriggerRepository;
import com.dinesh.repository.DcCaseRepo;
import com.dinesh.repository.DcChildrenRepo;
import com.dinesh.repository.DcEducationRepo;
import com.dinesh.repository.DcIncomeRepo;
import com.dinesh.repository.EligDtlsRepository;
import com.dinesh.repository.PlanRepository;
import com.dinesh.response.EligResponse;




@Service
public class EligServiceImpl implements EligService {
	@Autowired
	private DcCaseRepo dcCaseRepo;

	@Autowired
	private PlanRepository planRepo;

	@Autowired
	private DcIncomeRepo incomeRepo;

	@Autowired
	private DcChildrenRepo childRepo;

	@Autowired
	private CitizenAppRepository appRepo;

	@Autowired
	private DcEducationRepo eduRepo;

	@Autowired
	private EligDtlsRepository eligRepo;

	@Autowired
	private CoTriggerRepository coTrgRepo;;

	@Override
	public EligResponse determineEligibility(Long caseNum) {
		Optional<DcCaseEntity> caseEntity = dcCaseRepo.findById(caseNum);
		Integer planId = null;
		String planName = null;
		Integer appId = null;
		if (caseEntity.isPresent()) {
			DcCaseEntity dcCaseEntity = caseEntity.get();
			planId = dcCaseEntity.getPlanId();
			appId = dcCaseEntity.getAppId();
		}

		Optional<PlanEntity> planEntity = planRepo.findById(planId);
		if (planEntity.isPresent()) {
			PlanEntity plan = planEntity.get();
			planName = plan.getPlanName();
		}
		Optional<CitizenAppEntity> app = appRepo.findById(appId);
		Integer age = 0;
		CitizenAppEntity citizenAppEntity = null;
		if (app.isPresent()) {
			citizenAppEntity = app.get();
			LocalDate dob = citizenAppEntity.getDob();
			LocalDate now = LocalDate.now();
			age = Period.between(dob, now).getYears();
		}
		EligResponse eligResponse = executePlanConditions(caseNum, planName, age);
		// logic to store data in db
		EligDtlsEntity eligEntity = new EligDtlsEntity();
		BeanUtils.copyProperties(eligResponse, eligEntity);

		eligEntity.setCaseNum(caseNum);
		eligEntity.setHolderName(citizenAppEntity.getFullName());
		eligEntity.setHolderSsn(citizenAppEntity.getSsn());

		eligRepo.save(eligEntity);

		CoTriggerEntity coEntity = new CoTriggerEntity();
		coEntity.setCaseNum(caseNum);
		coEntity.setTrgStatus("Pending");

		coTrgRepo.save(coEntity);
		
		return eligResponse;
	}

	private EligResponse executePlanConditions(Long caseNum, String planName, Integer age) {
		EligResponse response = new EligResponse();
		response.setPlanName(planName);
		DcIncomeEntity income = incomeRepo.findByCaseNum(caseNum);
		if ("SNAP".equals(planName)) {
			Double empIncome = income.getEmpIncome();
			if (empIncome <= 300) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Denied");
				response.setDenialReason("High Income");
			}

		} else if ("CCAP".equals(planName)) {
			boolean ageCondition = true;
			boolean kidsCountCondition = false;
			List<DcChildrenEntity> childs = childRepo.findByCaseNum(caseNum);
			if (!childs.isEmpty()) {
				kidsCountCondition = true;
				for (DcChildrenEntity entity : childs) {
					Integer childAge = entity.getChildAge();
					if (childAge >= 16) {
						ageCondition = false;
						break;
					}
				}
			}
			if (income.getEmpIncome() <= 300 && kidsCountCondition && ageCondition) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Denined");
				response.setDenialReason("Not Satisfied");
			}
		} else if ("MediCaid".equals(planName)) {
			Double empIncome = income.getEmpIncome();
			Double propertyIncome = income.getPropertyIncome();
			if (empIncome < 300 && propertyIncome == 0) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Deniened");
				response.setDenialReason("High Income");
			}

		} else if ("MediCare".equals(planName)) {

			if (age > 65) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Denined");
				response.setDenialReason("Age not Matched");
			}

		} else if ("NJW".equals(planName)) {
			DcEducationEntity educationEntity = eduRepo.findByCaseNum(caseNum);
			Integer gratuationYear = educationEntity.getGratuationYear();
			int currYear = LocalDate.now().getYear();
			if (income.getEmpIncome() <= 0 && gratuationYear < currYear) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Denined");
				response.setDenialReason("Rules Not Satisfied");
			}
		}
		if (response.getPlanStatus().equals("Approved")) {
			response.setPlanStartDate(LocalDate.now());
			response.setPlanEndDate(LocalDate.now().plusMonths(6));
			response.setBenifitAmt(350.00);

		}
		return response;
	}

		}


		
	



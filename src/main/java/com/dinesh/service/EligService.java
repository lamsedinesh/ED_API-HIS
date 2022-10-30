package com.dinesh.service;

import javax.servlet.http.HttpServletResponse;

import com.dinesh.entity.CoTriggerEntity;
import com.dinesh.entity.EligDtlsEntity;
import com.dinesh.response.EligResponse;

public interface EligService {
	public EligResponse determineEligibility(Long caseNum);
	


	

}

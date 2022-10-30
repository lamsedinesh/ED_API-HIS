package com.dinesh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "DC_INCOME")
@Data
public class DcIncomeEntity {

	@Id
	@GeneratedValue
	private Integer incomeId;
	private Long caseNum;
	private Double empIncome;
	private Double propertyIncome;
}

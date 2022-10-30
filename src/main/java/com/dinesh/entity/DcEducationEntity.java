package com.dinesh.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "DC_EDUCATION")
@Data
public class DcEducationEntity {

	@Id
	@GeneratedValue
	private Integer eduId;
	private Long caseNum;
	private String highestQualification;
	private Integer gratuationYear;
}

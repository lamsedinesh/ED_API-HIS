package com.dinesh.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "DC_CHILDRENS")
@Data
public class DcChildrenEntity {

	@Id
	@GeneratedValue
	private Integer childrenId;
	private LocalDate dob;
	private Long ssn;
	private Long caseNum;
	private Integer childAge;
}

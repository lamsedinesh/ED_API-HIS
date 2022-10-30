package com.dinesh.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "PLAN_MASTER")
public class PlanEntity {
	@Id
	@GeneratedValue
	@Column(name = "PLAN_ID")
	private Integer planId;

	@Column(name = "PLAN_NAME")
	private String planName;

	@Column(name = "PLAN_START_DATE")
	private LocalDate planStartDate;

	@Column(name = "PLAN_END_DATE")
	private LocalDate planEndDate;

	@Column(name = "PLAN_CATEGORY_ID")
	private Integer planCategoryId;

	@Column(name = "ACTIVE_SW")
	private String activeSw;

	@Column(name = "CREATE_DATE")
	private String createdBy;
	
	@Column(name = "UPDATE_DATE")
	private String updatedBy;

	@Column(name = "CREATED_BY", updatable = false)
	@CreationTimestamp
	private LocalDate createDate;

	@Column(name = "UPDATED_BY", insertable = false)
	@UpdateTimestamp
	private LocalDate updateDate;

}

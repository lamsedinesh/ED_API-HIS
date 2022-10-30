package com.dinesh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinesh.entity.DcEducationEntity;
@Repository
public interface DcEducationRepo extends JpaRepository<DcEducationEntity, Serializable> {
	public DcEducationEntity findByCaseNum(Long caseNum);
}

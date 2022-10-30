package com.dinesh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinesh.entity.DcIncomeEntity;
@Repository
public interface DcIncomeRepo extends JpaRepository<DcIncomeEntity, Serializable> {
	public DcIncomeEntity findByCaseNum(Long caseNum);
}

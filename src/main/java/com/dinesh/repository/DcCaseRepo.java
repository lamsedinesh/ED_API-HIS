package com.dinesh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinesh.entity.DcCaseEntity;

@Repository
public interface DcCaseRepo extends JpaRepository<DcCaseEntity, Serializable> {
	public DcCaseEntity findByAppId(Integer appId);
}

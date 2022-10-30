package com.dinesh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinesh.entity.EligDtlsEntity;

@Repository
public interface EligDtlsRepository extends JpaRepository<EligDtlsEntity, Serializable> {

}

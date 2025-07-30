package com.project.adopt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.adopt.entity.AdoptEntity;

public interface AdoptRepository extends JpaRepository<AdoptEntity, Long>{

}

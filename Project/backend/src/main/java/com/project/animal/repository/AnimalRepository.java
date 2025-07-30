package com.project.animal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.animal.entity.AnimalEntity;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long>{

}

package com.project.animal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.animal.entity.AnimalEntity;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long>{
	
	@Query("""
	        SELECT f
	        FROM FileUploadEntity f
	        JOIN f.bbs b
	        JOIN b.member m
	        JOIN AdoptEntity a ON a.member = m
	        JOIN a.animal an
	        WHERE an.animalId = :animalId
	    """)
	
	    List<FileUpLoadEntity> findFileUploadsByAnimalId(@Param("animalId") Long animalId);
	}

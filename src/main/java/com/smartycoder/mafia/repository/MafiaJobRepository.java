package com.smartycoder.mafia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartycoder.mafia.entity.MafiaJob;
import com.smartycoder.mafia.entity.ManipulationStatus;

public interface MafiaJobRepository extends JpaRepository<MafiaJob, Long> {

	MafiaJob findByUuid(String uuid);
	
	Page<MafiaJob> findAllByStatus(ManipulationStatus status, Pageable pageable);

}

package com.smartycoder.mafia.service;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.smartycoder.mafia.dto.MafiaJobOutputDto;
import com.smartycoder.mafia.dto.MafiaJobRequestDto;
import com.smartycoder.mafia.dto.MafiaJobStatusDto;
import com.smartycoder.mafia.dto.UuidDto;

public interface MafiaJobService {
	
	UuidDto createMafiaJob(MafiaJobRequestDto dto, MultipartFile file);

	boolean manipulationStarted(Long id);

	void manipulationFinished(Long mafiaJobId, long durationInMiliseconds, Path outputFilePath);

	void manipulationFailed(Long mafiaJobId, Throwable e, long durationInMiliseconds);

	MafiaJobStatusDto getStatus(String uuid);

	MafiaJobOutputDto outputRequested(String uuid); 
}

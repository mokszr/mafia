package com.smartycoder.mafia.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartycoder.mafia.constant.FileDirectoryType;
import com.smartycoder.mafia.dto.MafiaJobOutputDto;
import com.smartycoder.mafia.dto.MafiaJobRequestDto;
import com.smartycoder.mafia.dto.MafiaJobStatusDto;
import com.smartycoder.mafia.dto.UuidDto;
import com.smartycoder.mafia.entity.MafiaJob;
import com.smartycoder.mafia.entity.ManipulationStatus;
import com.smartycoder.mafia.event.ExecuteNewMafiaJobEvent;
import com.smartycoder.mafia.exception.MafiaJobNotFinishedException;
import com.smartycoder.mafia.exception.MafiaJobNotFoundException;
import com.smartycoder.mafia.exception.ManipulationNotFoundException;
import com.smartycoder.mafia.repository.MafiaJobRepository;
import com.smartycoder.mafia.service.MafiaJobService;
import com.smartycoder.mafia.service.ManipulationStoreService;
import com.smartycoder.mafia.service.StorageService;

@Transactional(rollbackOn = Exception.class)
@Service
public class MafiaJobServiceImpl implements MafiaJobService {

	@Autowired
	private MafiaJobRepository mafiaJobRepository;
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private ManipulationStoreService manipulationStoreService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	//TODO set up validation
	@Override
	public UuidDto createMafiaJob(MafiaJobRequestDto dto, MultipartFile multipartFile) {

		String manipulationName = dto.getManipulationName();
		
		if(!manipulationStoreService.manipulationExist(manipulationName)) {
			
			throw new ManipulationNotFoundException(manipulationName);
		}
		
		String uuid = UUID.randomUUID().toString();
		
		storageService.store(multipartFile, uuid);
		
		MafiaJob entity = new MafiaJob();
		entity.setOriginalFileName(multipartFile.getOriginalFilename());
		entity.setContentType(multipartFile.getContentType());
		entity.setOriginalFileSize(multipartFile.getSize());
		entity.setPersistedFileName(uuid);
		entity.setUuid(uuid);
		entity.setManipulationName(dto.getManipulationName());
		entity.setManipulationParameters(dto.getManipulationParameters());
		entity.setFileType(dto.getFileType());
		
		entity.setCreatedOn(new Date());
		mafiaJobRepository.saveAndFlush(entity);

		UuidDto responseDto = new UuidDto();
		responseDto.setUuid(entity.getUuid());

		applicationEventPublisher.publishEvent(new ExecuteNewMafiaJobEvent(entity.getId()));

		return responseDto;
	}

	@Override
	public boolean manipulationStarted(Long id) {
		
		MafiaJob entity = mafiaJobRepository.getById(id);
		
		if(!ManipulationStatus.WAITING.equals(entity.getStatus())) {
			return false;
		}
		
		entity.setStatus(ManipulationStatus.PROCESSING);
		
		mafiaJobRepository.save(entity);
		
		return true;
	}

	@Override
	public void manipulationFinished(Long mafiaJobId, long durationInMiliseconds, Path outputFilePath) {
 
		MafiaJob entity = mafiaJobRepository.getById(mafiaJobId);

		File file = outputFilePath.toFile();
		
		entity.setDurationInMiliseconds(durationInMiliseconds);
		entity.setOutputFileName(file.getName());
		entity.setOutputlFileSize(file.length());
		entity.setStatus(ManipulationStatus.FINISHED);
		entity.setExecutionFinishedOn(new Date());
		 
		mafiaJobRepository.save(entity);
	}
	
	@Override
	public void manipulationFailed(Long mafiaJobId, Throwable e, long durationInMiliseconds) {
		
		MafiaJob entity = mafiaJobRepository.getById(mafiaJobId);
		
		entity.setDurationInMiliseconds(durationInMiliseconds);
		entity.setStatus(ManipulationStatus.FAILED);
		entity.setExecutionFinishedOn(new Date());
		entity.setExceptionClassName(e.getClass().getName());

		mafiaJobRepository.save(entity);

	}

	@Override
	public MafiaJobStatusDto getStatus(String uuid) {
		MafiaJob foundEntity = mafiaJobRepository.findByUuid(uuid);
		
		if(foundEntity == null) {
			throw new MafiaJobNotFoundException(uuid);
		}
		
		MafiaJobStatusDto statusDto = new MafiaJobStatusDto();
		statusDto.setStatus(foundEntity.getStatus());
		statusDto.setOutputlFileSize(foundEntity.getOutputlFileSize());
		statusDto.setDurationInMiliseconds(foundEntity.getDurationInMiliseconds());
		
 		return statusDto;
	}

	@Override
	public MafiaJobOutputDto outputRequested(String uuid) {
		MafiaJob foundEntity = mafiaJobRepository.findByUuid(uuid);
		
		if(foundEntity == null) {
			throw new MafiaJobNotFoundException(uuid);
		}
		
		ManipulationStatus status = foundEntity.getStatus();
		
		if(!ManipulationStatus.FINISHED.equals(status)) {
			throw new MafiaJobNotFinishedException(status);
		}
		
		String outputFileName = foundEntity.getOutputFileName();
		
		Resource file = storageService.loadAsResource(outputFileName, FileDirectoryType.OUTPUT);
		
		String manipulationName = foundEntity.getManipulationName();
		
		String attachmentFileName = manipulationName.trim().replaceAll(" ", "_") + "_output_" + uuid;
		
		MafiaJobOutputDto output = new MafiaJobOutputDto();
		output.setAttachmentFilename(attachmentFileName);
		output.setFile(file);
		
		return output;
	}

}

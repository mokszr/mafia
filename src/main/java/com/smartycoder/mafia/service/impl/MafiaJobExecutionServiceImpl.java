package com.smartycoder.mafia.service.impl;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smartycoder.mafia.entity.MafiaJob;
import com.smartycoder.mafia.entity.ManipulationStatus;
import com.smartycoder.mafia.event.ExecuteNewMafiaJobEvent;
import com.smartycoder.mafia.manipulation.BaseManipulation;
import com.smartycoder.mafia.repository.MafiaJobRepository;
import com.smartycoder.mafia.service.MafiaJobExecutionService;
import com.smartycoder.mafia.service.MafiaJobService;
import com.smartycoder.mafia.service.ManipulationStoreService;
import com.smartycoder.mafia.service.StorageService;

@Transactional(rollbackOn = Exception.class)
@Service
public class MafiaJobExecutionServiceImpl implements MafiaJobExecutionService {

	@Autowired
	private MafiaJobRepository mafiaJobRepository;
	
	@Autowired
	private ManipulationStoreService manipulationStoreService;
	
	@Autowired
	private MafiaJobService mafiaJobService;
	
	@Autowired
	private StorageService storageService;
	
	@Value( "${mafia.executorThreadPoolSize}")
	private int mafiaExecutorThreadPoolSize;
	
	private ExecutorService executorService;
	
	@PostConstruct
	public void initExecutorService() {
		
		//TODO: use logger here
		System.out.println("mafiaExecutorThreadPoolSize " + mafiaExecutorThreadPoolSize);
		
		executorService = Executors.newFixedThreadPool(mafiaExecutorThreadPoolSize);
	}
	
	@Async
	@EventListener
	@Override
	public void executeMafiaJob(ExecuteNewMafiaJobEvent event) {
		
		Optional<MafiaJob> optionalMafiaJob = mafiaJobRepository.findById(event.getMafiaJobId());

		if(!optionalMafiaJob.isPresent()) {
			return;
		}
		
		MafiaJob mafiaJob = optionalMafiaJob.get();
		
		//System.out.println("event listened " + mafiaJob.getUuid());
		
		invokeExecutorService(mafiaJob);
		
	}

	private void invokeExecutorService(MafiaJob mafiaJob) {
		Path uploadedFilePath = storageService.loadFromUploadDirectory(mafiaJob.getPersistedFileName());
		
		String outputFileName = UUID.randomUUID().toString();
		
		Path outputFilePath = storageService.loadFromOutputDirectory(outputFileName);
		
		BaseManipulation manipulationInstance = manipulationStoreService.createManipulationInstance(mafiaJob.getManipulationName());
		
		manipulationInstance.setUploadedFilePath(uploadedFilePath);
		manipulationInstance.setOutputFilePath(outputFilePath);
		manipulationInstance.setMafiaJobService(mafiaJobService);
		manipulationInstance.setMafiaJobId(mafiaJob.getId());
		manipulationInstance.setManipulationParameters(mafiaJob.getManipulationParameters());
		
		executorService.execute(manipulationInstance);
	}

	@Scheduled(fixedDelay = 5000)
	@Override
	public void executeWaitingJobsScheduled() {
		
		Page<MafiaJob> findAllByStatus = mafiaJobRepository.findAllByStatus(ManipulationStatus.WAITING, Pageable.ofSize(20));

		System.out.println("executeWaitingJobsScheduled waiting status page: " + findAllByStatus.getSize() + " total elements: " + findAllByStatus.getTotalElements());
		
		for (MafiaJob mafiaJob : findAllByStatus) {
			invokeExecutorService(mafiaJob);
		}
		
	}
	
}

package com.smartycoder.mafia.manipulation;

import java.nio.file.Path;
import java.util.Map;

import com.smartycoder.mafia.service.MafiaJobService;

public abstract class AbstractManipulation implements BaseManipulation {

	private long startingTime;
	
	private long endingTime;
	
	private MafiaJobService mafiaJobService;

	private Long mafiaJobId;
	
	private Path uploadedFilePath;

	private Path outputFilePath;

	private Map<String, String> manipulationParameters;
	
	@Override
	public void run() {
		 try {
			 
			 startingTime = System.currentTimeMillis();
			 
			 boolean isStartedSuccessfuly = onManipulationStarted();
			 if(!isStartedSuccessfuly) {
				 return;
			 }
			 
			 applyManipulation(uploadedFilePath, outputFilePath, manipulationParameters);
			 
			 endingTime = System.currentTimeMillis();
			 
			 onManipulationFinished(endingTime - startingTime, outputFilePath);
			 
		 } catch (Throwable e) {
			 
			 endingTime = System.currentTimeMillis();
			 
			 onFailedWithException(e, endingTime - startingTime);
			 
		 }

	}
	
	@Override
	public boolean onManipulationStarted() {
		
		return mafiaJobService.manipulationStarted(mafiaJobId);
	}

	@Override
	public void onManipulationFinished(long durationInMiliseconds, Path outputFilePath) {
		
		mafiaJobService.manipulationFinished(mafiaJobId, durationInMiliseconds, outputFilePath);
	}

	@Override
	public void onFailedWithException(Throwable e, long durationInMiliseconds) {
 
		mafiaJobService.manipulationFailed(mafiaJobId, e, durationInMiliseconds);
	}
	
	@Override
	public void setMafiaJobService(MafiaJobService mafiaJobService) {
		this.mafiaJobService = mafiaJobService;
	}

	@Override
	public void setUploadedFilePath(Path uploadedFilePath) {
		this.uploadedFilePath = uploadedFilePath;
	}
	
	@Override
	public void setOutputFilePath(Path outputFilePath) {
		this.outputFilePath = outputFilePath;
	}
	
	@Override
	public void setMafiaJobId(Long mafiaJobId) {
		this.mafiaJobId = mafiaJobId;
	}

	@Override
	public 	void setManipulationParameters(Map<String, String> manipulationParameters) {
		this.manipulationParameters = manipulationParameters;
	}
}

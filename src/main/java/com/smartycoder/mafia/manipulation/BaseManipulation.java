package com.smartycoder.mafia.manipulation;

import java.nio.file.Path;
import java.util.Map;

import com.smartycoder.mafia.service.MafiaJobService;

public interface BaseManipulation extends Runnable {

	boolean onManipulationStarted();
	
	void onManipulationFinished(long durationInMiliseconds, Path outputFilePath);
	
	void onFailedWithException(Throwable e, long durationInMiliseconds);
	
	void applyManipulation(Path uploadedFilePath, Path outputFilePath, Map<String, String> manipulationParameters) throws Throwable;
	
	void setMafiaJobService(MafiaJobService mafiaJobService);

	void setUploadedFilePath(Path uploadedFilePath);

	void setOutputFilePath(Path outputFilePath);

	void setMafiaJobId(Long mafiaJobId);

	void setManipulationParameters(Map<String, String> manipulationParameters);

}

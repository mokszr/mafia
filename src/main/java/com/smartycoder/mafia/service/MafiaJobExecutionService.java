package com.smartycoder.mafia.service;

import com.smartycoder.mafia.event.ExecuteNewMafiaJobEvent;

public interface MafiaJobExecutionService {

	void executeMafiaJob(ExecuteNewMafiaJobEvent event);
	
	void executeWaitingJobsScheduled();
}

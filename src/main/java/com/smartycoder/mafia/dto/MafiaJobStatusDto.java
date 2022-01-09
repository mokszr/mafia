package com.smartycoder.mafia.dto;

import com.smartycoder.mafia.entity.ManipulationStatus;

public class MafiaJobStatusDto {
	
	private ManipulationStatus status;
	
	private long outputlFileSize;
	
	private long durationInMiliseconds;

	public ManipulationStatus getStatus() {
		return status;
	}

	public void setStatus(ManipulationStatus status) {
		this.status = status;
	}

	public long getOutputlFileSize() {
		return outputlFileSize;
	}

	public void setOutputlFileSize(long outputlFileSize) {
		this.outputlFileSize = outputlFileSize;
	}

	public long getDurationInMiliseconds() {
		return durationInMiliseconds;
	}

	public void setDurationInMiliseconds(long durationInMiliseconds) {
		this.durationInMiliseconds = durationInMiliseconds;
	}
	
}

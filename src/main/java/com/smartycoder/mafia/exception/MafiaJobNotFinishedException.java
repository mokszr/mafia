package com.smartycoder.mafia.exception;

import com.smartycoder.mafia.entity.ManipulationStatus;

public class MafiaJobNotFinishedException extends RuntimeException {

	private ManipulationStatus status;

	public MafiaJobNotFinishedException(ManipulationStatus status) {
		super(String.valueOf(status));
		this.status = status;
	}

	public ManipulationStatus getStatus() {
		return status;
	}
 
}
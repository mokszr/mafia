package com.smartycoder.mafia.event;

public class ExecuteNewMafiaJobEvent {

	private long mafiaJobId;

	public ExecuteNewMafiaJobEvent(long mafiaJobId) {
		this.mafiaJobId = mafiaJobId;
	}

	public long getMafiaJobId() {
		return mafiaJobId;
	}
	
}

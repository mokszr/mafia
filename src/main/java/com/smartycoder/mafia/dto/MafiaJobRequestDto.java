package com.smartycoder.mafia.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.smartycoder.mafia.entity.FileType;

public class MafiaJobRequestDto {
	
	private FileType fileType;
	
	@NotNull
	private String manipulationName;
	
	private Map<String, String> manipulationParameters = new HashMap<>();
	
	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getManipulationName() {
		return manipulationName;
	}

	public void setManipulationName(String manipulationName) {
		this.manipulationName = manipulationName;
	}

	public Map<String, String> getManipulationParameters() {
		return manipulationParameters;
	}

	public void setManipulationParameters(Map<String, String> manipulationParameters) {
		this.manipulationParameters = manipulationParameters;
	}

}

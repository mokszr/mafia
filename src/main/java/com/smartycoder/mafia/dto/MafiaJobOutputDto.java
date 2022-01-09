package com.smartycoder.mafia.dto;

import org.springframework.core.io.Resource;

public class MafiaJobOutputDto {

	private Resource file;
	
	private String attachmentFilename;

	public Resource getFile() {
		return file;
	}

	public void setFile(Resource file) {
		this.file = file;
	}

	public String getAttachmentFilename() {
		return attachmentFilename;
	}

	public void setAttachmentFilename(String attachmentFilename) {
		this.attachmentFilename = attachmentFilename;
	}
	
}

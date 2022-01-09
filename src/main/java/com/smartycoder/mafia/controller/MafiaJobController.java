package com.smartycoder.mafia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartycoder.mafia.dto.MafiaJobOutputDto;
import com.smartycoder.mafia.dto.MafiaJobRequestDto;
import com.smartycoder.mafia.dto.MafiaJobStatusDto;
import com.smartycoder.mafia.dto.UuidDto;
import com.smartycoder.mafia.service.MafiaJobService;

@RestController
@RequestMapping(path = "/job-management/mafia-jobs")
public class MafiaJobController {

	@Autowired
	private MafiaJobService mafiaJobService;

	@PostMapping()
	public UuidDto createMafiaJob(@RequestPart("file") MultipartFile file,
			@RequestPart("mafiaJob") MafiaJobRequestDto mafiaJob) {

		return mafiaJobService.createMafiaJob(mafiaJob, file);
	}

	@GetMapping(value = "/status/{uuid}")
	public MafiaJobStatusDto getStatus(@PathVariable String uuid) {
		return mafiaJobService.getStatus(uuid);
	}

	@GetMapping(value = "/output/{uuid}")
	public ResponseEntity<Resource> getOutput(@PathVariable String uuid) {
		
		MafiaJobOutputDto output = mafiaJobService.outputRequested(uuid);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + output.getAttachmentFilename() + "\"").body(output.getFile());
	}
	
	//TODO delete endpoint should be added
}

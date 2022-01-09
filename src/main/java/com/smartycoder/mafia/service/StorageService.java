package com.smartycoder.mafia.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.smartycoder.mafia.constant.FileDirectoryType;

public interface StorageService {

	void init();

	void store(MultipartFile file, String fileName);

	Path loadFromUploadDirectory(String filename);

	Path loadFromOutputDirectory(String filename);

	Resource loadAsResource(String filename, FileDirectoryType directoryType);

	void deleteFromUploadDirectory(String filename);

	void deleteFromDownloadDirectory(String filename);


 
}
package com.smartycoder.mafia.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartycoder.mafia.constant.FileDirectoryType;
import com.smartycoder.mafia.exception.StorageException;
import com.smartycoder.mafia.exception.StorageFileNotFoundException;
import com.smartycoder.mafia.exception.UnknownDirectoryTypeStorageException;
import com.smartycoder.mafia.service.StorageService;

@Service
public class FileSystemStorageService implements StorageService {

	private Path uploadLocation;

	private Path outputLocation;

	@Value("${mafia.uploadDirectory}")
	private String uploadDirectoryPath;

	@Value("${mafia.outputDirectory}")
	private String outputDirectoryPath;

	@PostConstruct
	@Override
	public void init() {

		uploadLocation = Paths.get(uploadDirectoryPath);

		outputLocation = Paths.get(outputDirectoryPath);

		try {
			Files.createDirectories(uploadLocation);
			Files.createDirectories(outputLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile file, String fileName) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.uploadLocation.resolve(Paths.get(fileName)).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().normalize().equals(this.uploadLocation.toAbsolutePath().normalize())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Path loadFromUploadDirectory(String filename) {
		return uploadLocation.resolve(filename);
	}

	@Override
	public Path loadFromOutputDirectory(String filename) {
		return outputLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename, FileDirectoryType directoryType) {
		try {

			Path file = null;

			if (FileDirectoryType.OUTPUT.equals(directoryType)) {

				file = loadFromOutputDirectory(filename);

			} else if (FileDirectoryType.UPLOAD.equals(directoryType)) {

				file = loadFromUploadDirectory(filename);

			} else {

				throw new UnknownDirectoryTypeStorageException("Unknown directory type: " + directoryType);

			}

			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {

				return resource;

			} else {

				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}

		} catch (MalformedURLException e) {

			throw new StorageFileNotFoundException("Could not read file: " + filename, e);

		}
	}

	@Override
	public void deleteFromUploadDirectory(String filename) {
		try {
			Files.delete(uploadLocation.resolve(filename));
		} catch (IOException e) {
			throw new StorageException("Failed to delete file.", e);
		}
	}

	@Override
	public void deleteFromDownloadDirectory(String filename) {
		try {
			Files.delete(outputLocation.resolve(filename));
		} catch (IOException e) {
			throw new StorageException("Failed to delete file.", e);
		}
	}
}
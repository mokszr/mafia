package com.smartycoder.mafia.store;

import java.nio.file.Path;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.smartycoder.mafia.manipulation.AbstractManipulation;

public class JustCopyManipulation extends AbstractManipulation {

	@Override
	public void applyManipulation(Path uploadedFilePath, Path outputFilePath,
			Map<String, String> manipulationParameters) throws Throwable {

		 FileUtils.copyFile(uploadedFilePath.toFile(), outputFilePath.toFile());
	}
}

package com.smartycoder.mafia.store;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import javax.imageio.ImageIO;

import com.smartycoder.mafia.manipulation.AbstractManipulation;


public class ScaleImageManipulation extends AbstractManipulation {

	@Override
	public void applyManipulation(Path uploadedFilePath, Path outputFilePath,
			Map<String, String> manipulationParameters) throws Throwable {

		Double scale = Double.valueOf(manipulationParameters.get("scale"));
		
		BufferedImage readImage = ImageIO.read(uploadedFilePath.toFile());
		
		int width = (int) (readImage.getWidth() * scale);
		int height = (int) (readImage.getHeight() * scale);
		
		Image scaledInstance = readImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics g = bi.getGraphics(); 
	    
	    try { 
	        g.drawImage(scaledInstance, 0, 0, null);
	        ImageIO.write(bi, "png", outputFilePath.toFile()); 
	    } catch (IOException e) { 
	        throw new IllegalStateException("writing image failed", e);
	    } finally {
			g.dispose();
		}		 
		 
	}
	
	
}

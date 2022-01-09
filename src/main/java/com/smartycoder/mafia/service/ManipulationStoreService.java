package com.smartycoder.mafia.service;

import com.smartycoder.mafia.manipulation.BaseManipulation;

public interface ManipulationStoreService {

	void registerManipulation(String name, Class<? extends BaseManipulation> clazz);
	
	BaseManipulation createManipulationInstance(String name);
	
	boolean manipulationExist(String manipulationName);
	
}

package com.smartycoder.mafia.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.smartycoder.mafia.exception.ManipulationCanNotBeInitializedException;
import com.smartycoder.mafia.exception.ManipulationNotFoundException;
import com.smartycoder.mafia.manipulation.BaseManipulation;
import com.smartycoder.mafia.service.ManipulationStoreService;

@Service
public class ManipulationStoreServiceImpl implements ManipulationStoreService {

	private Map<String, Class<? extends BaseManipulation>> manipulationClasses = new ConcurrentHashMap<>();
	
	@Override
	public void registerManipulation(String name, Class<? extends BaseManipulation> clazz) {

		manipulationClasses.put(name, clazz);

	}

	@Override
	public BaseManipulation createManipulationInstance(String name){
		
		Class<? extends BaseManipulation> clazz = manipulationClasses.get(name);
		
		if(clazz == null) {
			
			throw new ManipulationNotFoundException(name);
		}
		
 		try {
			
 			return clazz.getDeclaredConstructor().newInstance();
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {

			throw new ManipulationCanNotBeInitializedException("\"" +name + "\n anipulation can not be initialized", e);
		}
	}

	@Override
	public boolean manipulationExist(String manipulationName) {
		
		if(manipulationName == null || manipulationName.isBlank()) {
			return false;
		}
		
		return manipulationClasses.containsKey(manipulationName);
	}

}

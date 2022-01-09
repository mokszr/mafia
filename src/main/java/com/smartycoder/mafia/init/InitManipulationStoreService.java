package com.smartycoder.mafia.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.smartycoder.mafia.service.ManipulationStoreService;
import com.smartycoder.mafia.store.ScaleImageManipulation;
import com.smartycoder.mafia.store.JustCopyManipulation;

@Component
@Order(1)
public class InitManipulationStoreService implements ApplicationListener<ApplicationReadyEvent>{

	@Autowired
	private ManipulationStoreService manipulationStoreService;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		manipulationStoreService.registerManipulation("justcopy", JustCopyManipulation.class);
		manipulationStoreService.registerManipulation("scale_image", ScaleImageManipulation.class);
	}

}

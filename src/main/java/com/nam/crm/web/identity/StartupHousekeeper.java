/**
 * 
 */
package com.nam.crm.web.identity;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Nikhil 06-May-2018
 */

@Component
public class StartupHousekeeper {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		String rootPath = System.getProperty("catalina.home");
		String excels = rootPath + File.separator + "excels";
		String temp = rootPath + File.separator + "temp";
		String config = rootPath + File.separator + "config";
		File excelDir = new File(excels);
		File tempDir = new File(temp);
		File confDir = new File(config);
		
		logger.info("rootPath : "+excels);
		if (!excelDir.exists()) {
			excelDir.mkdirs();
			logger.info("tmpFiles created on "+ rootPath);
		}
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		if (!confDir.exists()) {
			confDir.mkdirs();
		}
	}
}
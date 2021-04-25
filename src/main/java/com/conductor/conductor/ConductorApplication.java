package com.conductor.conductor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class ConductorApplication {
	private static final Logger log = LoggerFactory.getLogger(ConductorApplication.class);
	public static void main(String[] args) throws IOException {
		loadExternalConfig();
		SpringApplication.run(ConductorApplication.class, args);
	}
	private static void loadExternalConfig() throws IOException {
		String configFile = System.getProperty("CONDUCTOR_CONFIG_FILE");
		if (!StringUtils.isEmpty(configFile)) {
			FileSystemResource resource = new FileSystemResource(configFile);
			if (resource.exists()) {
				Properties properties = new Properties();
				properties.load(resource.getInputStream());
				properties.forEach((key, value) -> System.setProperty((String) key, (String) value));
				log.info("Loaded {} properties from {}", properties.size(), configFile);
			}else {
				log.warn("Ignoring {} since it does not exist", configFile);
			}
	}
	}

}

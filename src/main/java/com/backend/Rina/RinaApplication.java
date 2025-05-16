package com.backend.Rina;

import com.backend.Rina.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class RinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RinaApplication.class, args);
	}

}

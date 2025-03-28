package com.backend.Rina;

import com.backend.Rina.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class RinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RinaApplication.class, args);
	}

}

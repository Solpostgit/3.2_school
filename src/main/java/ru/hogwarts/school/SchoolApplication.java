package ru.hogwarts.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApplication {

	private static final Logger logger = LoggerFactory.getLogger(SchoolApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);

		logger.info("Application successfully run!");
	}

}

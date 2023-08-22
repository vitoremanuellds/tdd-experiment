package com.ufcg.taskgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
		//(ScanBasePackages = "com.ufcg.taskgenerator.*")
public class TaskGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskGeneratorApplication.class, args);
	}

}

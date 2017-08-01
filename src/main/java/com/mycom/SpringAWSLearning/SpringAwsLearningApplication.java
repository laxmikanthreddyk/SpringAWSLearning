package com.mycom.SpringAWSLearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.mycom.SpringAWSLearning.backend.persistence")
@EnableJpaRepositories(basePackages="com.mycom.SpringAWSLearning.backend.persistence")
public class SpringAwsLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAwsLearningApplication.class, args);
	}
}

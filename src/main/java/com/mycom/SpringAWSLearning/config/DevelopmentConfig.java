package com.mycom.SpringAWSLearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.mycom.SpringAWSLearning.backend.service.EmailService;
import com.mycom.SpringAWSLearning.backend.service.MockEmailService;

@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/development/config/application-dev.properties")
public class DevelopmentConfig {
	
	@Bean
	public EmailService emailService()
	{
		return new MockEmailService();
	}

}

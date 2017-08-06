package com.mycom.SpringAWSLearning.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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
	
	@Value("${stripe.test.private.key}")
	private String stripeDevKey;
	
	@Bean
	public EmailService emailService()
	{
		return new MockEmailService();
	}
	
	@Bean
	public ServletRegistrationBean h2ConsoleServletRegistration()
	{
		ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
		bean.addUrlMappings("/console/*");
		return bean;
	}
	
	@Bean
	public String stripeKey()
	{
		return stripeDevKey;
	}

}

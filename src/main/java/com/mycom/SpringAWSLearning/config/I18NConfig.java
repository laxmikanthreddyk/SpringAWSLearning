package com.mycom.SpringAWSLearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18NConfig {
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
	
		resourceBundleMessageSource.setBasename("classpath:i18N/messages");
		
		resourceBundleMessageSource.setCacheSeconds(1800);
		return resourceBundleMessageSource;
	}

}

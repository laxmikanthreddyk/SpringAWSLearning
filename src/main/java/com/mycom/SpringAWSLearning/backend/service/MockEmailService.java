package com.mycom.SpringAWSLearning.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.mycom.SpringAWSLearning.web.i18n.I18NService;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendGenericEmailMessage(SimpleMailMessage message) {
		
		LOG.debug("Simulating an Email Service...");
		LOG.info(message.toString());
		LOG.debug("Email Sent");
	}

	
}

package com.mycom.SpringAWSLearning.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.mycom.SpringAWSLearning.web.i18n.I18NService;

public class SMTPSendEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SMTPSendEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void sendGenericEmailMessage(SimpleMailMessage message) {
		
		LOG.debug("Simulating an Email Service...");
		LOG.info(message.toString());
		mailSender.send(message);
		LOG.debug("Email Sent");
	}

	
}

package com.mycom.SpringAWSLearning.backend.service;

import org.springframework.mail.SimpleMailMessage;

import com.mycom.SpringAWSLearning.web.domain.frontend.FeedbackPojo;

public interface EmailService {
	
	public void sendFeedbackEmail(FeedbackPojo feedbackPojo);
	
	public void sendGenericEmailMessage(SimpleMailMessage message);
	

}

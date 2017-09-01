package com.mycom.SpringAWSLearning.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.mycom.SpringAWSLearning.web.domain.frontend.FeedbackPojo;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.to.address}")
	private String defaultToAddress;

	protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedbackPojo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(defaultToAddress);
		message.setFrom(feedbackPojo.getEmail());
		message.setText(feedbackPojo.getFeedback());
		message.setSubject("[Devops Buddy]: Feedback received from " + feedbackPojo.getFirstName() + " "
				+ feedbackPojo.getLastName());
		return message;
	}
	
	@Override
	public void sendFeedbackEmail(FeedbackPojo feedbackPojo)
	{
		sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedbackPojo));
	}
}

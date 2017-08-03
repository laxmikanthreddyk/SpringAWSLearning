package com.mycom.SpringAWSLearning.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.PasswordResetToken;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.service.EmailService;
import com.mycom.SpringAWSLearning.backend.service.PasswordResetTokenService;
import com.mycom.SpringAWSLearning.utils.UserUtils;
import com.mycom.SpringAWSLearning.web.i18n.I18NService;

@Controller
public class ForgotMyPasswordController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
	
	public final static String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailform";
	
	public final static String FORGOT_PASSWORD_URL_MAPPING = "/forgotmypassword";
	
	public final static String EMAIL_MESSAGE_TEXT_PROPERTY_NAME="forgotmypassword.email.text";
	
	@Autowired
	private I18NService i18NService;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${webmaster.email}")
	private String webMasterEmail;
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	public final static String MAIL_SENT_KEY = "mailSent";
	
	
	@RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method=RequestMethod.GET)
	public String forgotPasswordGet()
	{
		return EMAIL_ADDRESS_VIEW_NAME;
	}
	
	@RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method=RequestMethod.POST)
	public String forgotPasswordPost(HttpServletRequest request, @RequestParam("email") String email, ModelMap model)
	{
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);
		
		if(null == passwordResetToken)
		{
			LOG.warn("No User found for given email to reset password {}",email);
		}
		else
		{
			User user = passwordResetToken.getUser();
			String token = passwordResetToken.getToken();
			String resetPasswordUrl=UserUtils.createPasswordResetUrl(request, user.getId(), token);
			
			String emailText = i18NService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME,request.getLocale());
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(user.getEmail());
			message.setSubject("[Devops Buddy]: How to Reset Your Password");
			message.setText(emailText + "\r\n"+resetPasswordUrl);
			message.setFrom(webMasterEmail);
			
			emailService.sendGenericEmailMessage(message);
			
			LOG.info("Password Token for given email {} with Token {}",email, passwordResetToken.getToken());
			LOG.debug("Reset Password url {}",resetPasswordUrl);
		}
		model.addAttribute(MAIL_SENT_KEY,"true");
		return EMAIL_ADDRESS_VIEW_NAME;
	}

}

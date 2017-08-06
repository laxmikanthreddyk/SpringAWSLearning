package com.mycom.SpringAWSLearning.controllers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.PasswordResetToken;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.service.EmailService;
import com.mycom.SpringAWSLearning.backend.service.PasswordResetTokenService;
import com.mycom.SpringAWSLearning.backend.service.UserService;
import com.mycom.SpringAWSLearning.utils.UserUtils;
import com.mycom.SpringAWSLearning.web.i18n.I18NService;

@Controller
public class ForgotMyPasswordController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
	
	public final static String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailform";
	
	public final static String FORGOT_PASSWORD_URL_MAPPING = "/forgotmypassword";
	
	public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";
	
	public final static String EMAIL_MESSAGE_TEXT_PROPERTY_NAME="forgotmypassword.email.text";
	
	public final static String CHANGE_PASSWORD_VIEW_NAME="forgotmypassword/changePassword";
	
    private static final String PASSWORD_RESET_ATTRIBUTE_NAME = "passwordReset";

    private static final String MESSAGE_ATTRIBUTE_NAME = "message";
	
	@Autowired
	private I18NService i18NService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
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
	
	@RequestMapping(value=CHANGE_PASSWORD_PATH,method=RequestMethod.GET)
	public String changeUserPasswordGet(@RequestParam("id") long id, @RequestParam("token") String token,
										Locale locale, ModelMap model)
	{
		 if (StringUtils.isEmpty(token) || id == 0) {
	            LOG.error("Invalid user id {}  or token value {}", id, token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Invalid user id or token value");
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

	        if (null == passwordResetToken) {
	            LOG.warn("A token couldn't be found with value {}", token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Token not found");
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        User user = passwordResetToken.getUser();
	        if (user.getId() != id) {
	            LOG.error("The user id {} passed as parameter does not match the user id {} associated with the token {}",
	                    id, user.getId(), token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, i18NService.getMessage("resetPassword.token.invalid", locale));
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
	            LOG.error("The token {} has expired", token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, i18NService.getMessage("resetPassword.token.expired", locale));
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        model.addAttribute("principalId", user.getId());

	        // OK to proceed. We auto-authenticate the user so that in the POST request we can check if the user
	        // is authenticated
	        Authentication auth = new UsernamePasswordAuthenticationToken(
	                user, null, user.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(auth);
		
		return CHANGE_PASSWORD_VIEW_NAME;
	}
	
	@RequestMapping(value = CHANGE_PASSWORD_PATH, method = RequestMethod.POST)
    public String changeUserPasswordPost(@RequestParam("principal_id") long userId,
                                         @RequestParam("password") String password,
                                         ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            LOG.error("An unauthenticated user tried to invoke the reset password POST method");
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request.");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        User user = (User) authentication.getPrincipal();
        if (user.getId() != userId) {
            LOG.error("Security breach! User {} is trying to make a password reset request on behalf of {}",
                    user.getId(), userId);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request.");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        userService.updateUserPassword(userId, password);
        LOG.info("Password successfully updated for user {}", user.getUsername());

        model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "true");

        return CHANGE_PASSWORD_VIEW_NAME;

    }

}

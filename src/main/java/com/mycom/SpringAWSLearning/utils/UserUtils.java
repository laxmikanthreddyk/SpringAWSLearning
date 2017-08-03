package com.mycom.SpringAWSLearning.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.controllers.ForgotMyPasswordController;

public class UserUtils {
	
	private UserUtils()
	{
		throw new AssertionError("Non instantiable");
		
	}

	public static User CreateBasicUser(String username, String email)
	{
		User user = new User();
		user.setUsername(username);
		user.setCountry("US");
		user.setEmail(email);
		user.setEnabled(true);
		user.setFirstName("Laxmikanth");
		user.setLastName("Koduru");
		user.setPhoneNumber("5516895560");
		user.setDescription("First User Account");
		user.setPassword("secret");
		user.setProfileImageUrl("http://test.com");
		
		return user;
	}

	public static String createPasswordResetUrl(HttpServletRequest request, long userId,
			String token) {
		// TODO Auto-generated method stub
		String passwordResetUrl=request.getScheme()+"://"+
								request.getServerName()+":"+
								request.getServerPort()+request.getContextPath()+
								ForgotMyPasswordController.FORGOT_PASSWORD_URL_MAPPING+
								"?id=" + userId+
								"&token="+token;
		return passwordResetUrl;
	}
}

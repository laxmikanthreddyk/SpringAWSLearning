package com.mycom.SpringAWSLearning.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.controllers.ForgotMyPasswordController;
import com.mycom.SpringAWSLearning.web.domain.frontend.BasicAccountPayload;

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
								ForgotMyPasswordController.CHANGE_PASSWORD_PATH+
								"?id=" + userId+
								"&token="+token;
		return passwordResetUrl;
	}

	public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T frontendPayload) {
        User user = new User();
        user.setUsername(frontendPayload.getUsername());
        user.setPassword(frontendPayload.getPassword());
        user.setFirstName(frontendPayload.getFirstName());
        user.setLastName(frontendPayload.getLastName());
        user.setEmail(frontendPayload.getEmail());
        user.setPhoneNumber(frontendPayload.getPhoneNumber());
        user.setCountry(frontendPayload.getCountry());
        user.setEnabled(true);
        user.setDescription(frontendPayload.getDescription());

        return user;
    }
}

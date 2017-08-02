package com.mycom.SpringAWSLearning.utils;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;

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
}

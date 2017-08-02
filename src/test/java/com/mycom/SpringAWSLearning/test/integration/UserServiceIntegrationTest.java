package com.mycom.SpringAWSLearning.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.mycom.SpringAWSLearning.SpringAwsLearningApplication;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Role;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.UserRole;
import com.mycom.SpringAWSLearning.backend.service.UserService;
import com.mycom.SpringAWSLearning.enums.PlansEnum;
import com.mycom.SpringAWSLearning.enums.RolesEnum;
import com.mycom.SpringAWSLearning.utils.UserUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testCreateUser() throws Exception
	{
		Set<UserRole> userRoles = new HashSet<>();
		User basicUser = UserUtils.CreateBasicUser(); 
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
		User user = userService.CreateUser(basicUser, PlansEnum.BASIC, userRoles);
		
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		
		
	}
}

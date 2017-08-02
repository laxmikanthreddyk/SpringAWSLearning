package com.mycom.SpringAWSLearning.test.integration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest{

	
	@Rule public TestName testName = new TestName();
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void testCreateUser() throws Exception
	{
		User user = CreateUser(testName);
		
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());	
		
	}

	@Test
	public void testUpdateUserPassword() throws Exception
	{
         User user = CreateUser(testName);
		
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());	
		String password = UUID.randomUUID().toString();
		
		userService.updateUserPassword(user.getId(), password);
		
		User userModified = userService.getUserDetails(user.getId());
		
		password = passwordEncoder.encode(password);
		
		Assert.assertNotNull(userModified);
		//Assert.assertEquals(password, userModified.getPassword());
	}
	
}

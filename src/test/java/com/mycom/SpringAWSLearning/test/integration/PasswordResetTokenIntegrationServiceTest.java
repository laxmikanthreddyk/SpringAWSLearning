package com.mycom.SpringAWSLearning.test.integration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycom.SpringAWSLearning.SpringAwsLearningApplication;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.PasswordResetToken;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.service.PasswordResetTokenService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class PasswordResetTokenIntegrationServiceTest extends AbstractServiceIntegrationTest {
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@Rule public TestName testName = new TestName();
	
	@Test
	public void testCreateNewTokenForUserEmail() throws Exception
	{
		User user = CreateUser(testName);
		
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
		Assert.assertNotNull(passwordResetToken);
		
		Assert.assertNotNull(passwordResetToken.getId());
	}
	
	@Test
	public void testFindByToken() throws Exception
	{
		User user = CreateUser(testName);
		
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
		Assert.assertNotNull(passwordResetToken);
		
		Assert.assertNotNull(passwordResetToken.getId());
		
		PasswordResetToken token = passwordResetTokenService.findByToken(passwordResetToken.getToken());
		
		Assert.assertNotNull(token);
		
		Assert.assertNotNull(token.getId());
	}
	

}

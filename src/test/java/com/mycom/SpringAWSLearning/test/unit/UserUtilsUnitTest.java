package com.mycom.SpringAWSLearning.test.unit;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycom.SpringAWSLearning.SpringAwsLearningApplication;
import com.mycom.SpringAWSLearning.controllers.ForgotMyPasswordController;
import com.mycom.SpringAWSLearning.utils.UserUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class UserUtilsUnitTest {

	private MockHttpServletRequest mockHttpServletRequest;
	
	@Before
	public void initi()
	{
		mockHttpServletRequest = new MockHttpServletRequest();
	}
	
	@Test
	public void testPasswordResetEmailUrlConstruction() throws Exception
	{
		mockHttpServletRequest.setServerPort(8080);
		String token = UUID.randomUUID().toString();
		long userId = 123456;
		String expectedUrl = "http://localhost:8080"+ForgotMyPasswordController.FORGOT_PASSWORD_URL_MAPPING+"?id="+userId+"&token="+token;
		String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest,userId, token);
		Assert.assertEquals(expectedUrl, actualUrl);
	}
}

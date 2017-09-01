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
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.controllers.ForgotMyPasswordController;
import com.mycom.SpringAWSLearning.utils.UserUtils;
import com.mycom.SpringAWSLearning.web.domain.frontend.BasicAccountPayload;

import junit.framework.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class UserUtilsUnitTest {

	private MockHttpServletRequest mockHttpServletRequest;
	
	private PodamFactory podamFactory;
	
	@Before
	public void initi()
	{
		mockHttpServletRequest = new MockHttpServletRequest();
		podamFactory = new PodamFactoryImpl();
	}
	
	@Test
	public void testPasswordResetEmailUrlConstruction() throws Exception
	{
		mockHttpServletRequest.setServerPort(8080);
		String token = UUID.randomUUID().toString();
		long userId = 123456;
		String expectedUrl = "http://localhost:8080"+ForgotMyPasswordController.CHANGE_PASSWORD_PATH+"?id="+userId+"&token="+token;
		String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest,userId, token);
		Assert.assertEquals(expectedUrl, actualUrl);
	}
	
	   @Test
	    public void mapWebUserToDomainUser() {

	        BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
	        webUser.setEmail("me@example.com");

	        User user = UserUtils.fromWebUserToDomainUser(webUser);
	        Assert.assertNotNull(user);

	        Assert.assertEquals(webUser.getUsername(), user.getUsername());
	        Assert.assertEquals(webUser.getPassword(), user.getPassword());
	        Assert.assertEquals(webUser.getFirstName(), user.getFirstName());
	        Assert.assertEquals(webUser.getLastName(), user.getLastName());
	        Assert.assertEquals(webUser.getEmail(), user.getEmail());
	        Assert.assertEquals(webUser.getPhoneNumber(), user.getPhoneNumber());
	        Assert.assertEquals(webUser.getCountry(), user.getCountry());
	        Assert.assertEquals(webUser.getDescription(), user.getDescription());

	    }
	
}

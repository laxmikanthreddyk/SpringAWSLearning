package com.mycom.SpringAWSLearning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mycom.SpringAWSLearning.web.i18n.I18NService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class SpringAwsLearningApplicationTests 
{
	
	@Autowired
	private I18NService i18NService;

    @Test
    public void testMessageByLocaleService() throws Exception
    {
    	 String expectedMessage = "Bootstrap starter template";
    	 String messageId = "index.main.callout";
    	 String actual = i18NService.getMessage(messageId);
    	 Assert.assertEquals("The actual and Expected Message Don't Match", expectedMessage, actual);
    }

}

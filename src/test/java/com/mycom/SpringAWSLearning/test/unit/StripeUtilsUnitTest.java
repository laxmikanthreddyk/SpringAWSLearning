package com.mycom.SpringAWSLearning.test.unit;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycom.SpringAWSLearning.SpringAwsLearningApplication;
import com.mycom.SpringAWSLearning.test.integration.StripeIntegrationTest;
import com.mycom.SpringAWSLearning.utils.StripeUtils;
import com.mycom.SpringAWSLearning.web.domain.frontend.ProAccountPayload;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class StripeUtilsUnitTest {

	 @Test
	    public void createStripeTokenParamsFromUserPayload() {

	        ProAccountPayload payload = new ProAccountPayload();
	        String cardNumber = StripeIntegrationTest.TEST_CC_NUMBER;
	        payload.setCardNumber(cardNumber);
	        String cardCode = StripeIntegrationTest.TEST_CC_CVC_NBR;
	        payload.setCardCode(cardCode);
	        String cardMonth = String.valueOf(StripeIntegrationTest.TEST_CC_EXP_MONTH);
	        payload.setCardMonth(cardMonth);
	        String cardYear = String.valueOf(LocalDate.now(Clock.systemUTC()).getYear() + 1);
	        payload.setCardYear(cardYear);

	        Map<String, Object> tokenParams = StripeUtils.extractTokenParamsFromSignupPayload(payload);
	        Map<String, Object> cardParams = (Map<String, Object>) tokenParams.get(StripeUtils.STRIPE_CARD_KEY);
	        assertThat(cardNumber, is(cardParams.get(StripeUtils.STRIPE_CARD_NUMBER_KEY)));
	        assertThat(cardMonth, is(String.valueOf(cardParams.get(StripeUtils.STRIPE_EXPIRY_MONTH_KEY))));
	        assertThat(cardYear, is(String.valueOf(cardParams.get(StripeUtils.STRIPE_EXPIRY_YEAR_KEY))));
	        assertThat(cardCode, is(cardParams.get(StripeUtils.STRIPE_CVC_KEY)));
	    }
	
}

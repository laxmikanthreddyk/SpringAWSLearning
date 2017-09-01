package com.mycom.SpringAWSLearning.exceptions;

import com.stripe.exception.AuthenticationException;

public class S3Exception extends RuntimeException {

	   public S3Exception(Throwable e) {
	        super(e);
	    }

	    public S3Exception(String s) {
	        super(s);
	    }
	
}

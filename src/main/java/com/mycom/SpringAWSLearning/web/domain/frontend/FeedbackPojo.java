package com.mycom.SpringAWSLearning.web.domain.frontend;

import java.io.Serializable;;

public class FeedbackPojo implements Serializable {

	public static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String email;
	private String feedback;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "FeedbackPojo [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", feedback="
				+ feedback + "]";
	}

}

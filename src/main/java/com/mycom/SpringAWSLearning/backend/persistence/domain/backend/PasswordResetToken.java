package com.mycom.SpringAWSLearning.backend.persistence.domain.backend;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;

import com.mycom.SpringAWSLearning.backend.persistence.converters.LocalDateTimeAttributeConverter;

@Entity
public class PasswordResetToken implements Serializable {
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_TOKEN_LENGTH_MINUTES = 120;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(unique=true)
	private String token;
	
	public PasswordResetToken()
	{
		
	}
	
	public PasswordResetToken(String token, User user, LocalDateTime creationDateTime, int expirationInMinutes) {
		if((user == null)|| (token == null)||(creationDateTime == null))
		{
			throw new IllegalArgumentException("user, token and expiry date cannot be null..");
		}
		if(expirationInMinutes ==0)
		{
			LOG.warn("The token expiration length in minutes is zero. Assigning default value {}",DEFAULT_TOKEN_LENGTH_MINUTES);
			expirationInMinutes = DEFAULT_TOKEN_LENGTH_MINUTES;
		}
		
		this.id = id;
		this.token = token;
		this.user = user;
	    expiryDate = creationDateTime.plusMinutes(expirationInMinutes);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="expiry_date")
	@Convert(converter=LocalDateTimeAttributeConverter.class)
	private LocalDateTime expiryDate;
	
	
}

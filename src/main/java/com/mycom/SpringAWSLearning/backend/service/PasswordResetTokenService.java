package com.mycom.SpringAWSLearning.backend.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.PasswordResetToken;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.PasswordResetTokenRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.UserRepository;

@Service
@Transactional(readOnly=true)
public class PasswordResetTokenService {

	 private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Value("${token.expiration.length.minutes}")
	private int tokenExpirationInMinutes;
	
	@Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email) {

        PasswordResetToken passwordResetToken = null;

        User user = userRepository.findByEmail(email);

        if (null != user) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token, user, now, tokenExpirationInMinutes);

            passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
            LOG.debug("Successfully created token {}  for user {}", token, user.getUsername());
        } else {
            LOG.warn("We couldn't find a user for the given email {}", email);
        }

        return passwordResetToken;

    }
	
	public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
	
}

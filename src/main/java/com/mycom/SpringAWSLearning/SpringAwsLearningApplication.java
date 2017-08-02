package com.mycom.SpringAWSLearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Role;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.UserRole;
import com.mycom.SpringAWSLearning.backend.service.SMTPSendEmailService;
import com.mycom.SpringAWSLearning.backend.service.UserService;
import com.mycom.SpringAWSLearning.enums.PlansEnum;
import com.mycom.SpringAWSLearning.enums.RolesEnum;
import com.mycom.SpringAWSLearning.utils.UserUtils;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SpringAwsLearningApplication implements CommandLineRunner  {

	private static final Logger LOG = LoggerFactory.getLogger(SMTPSendEmailService.class);
	
	@Value("${webmaster.username}")
	private String webmasterUsername;
	
	@Value("${webmaster.password}")
	private String webmasterPassword;
	
	@Value("${webmaster.email}")
	private String webmasterEmail;
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAwsLearningApplication.class, args);
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		
		Set<UserRole> userRoles = new HashSet<>();
		User basicUser = UserUtils.CreateBasicUser(webmasterUsername, webmasterEmail); 
		basicUser.setPassword(webmasterPassword);
		LOG.debug("Creating User with username {}",basicUser.getUsername());
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
		User user = userService.CreateUser(basicUser, PlansEnum.BASIC, userRoles);
		LOG.debug("User created with username {}",user.getUsername());
	}
}

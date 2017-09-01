package com.mycom.SpringAWSLearning.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Role;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.UserRole;
import com.mycom.SpringAWSLearning.backend.service.UserService;
import com.mycom.SpringAWSLearning.enums.PlansEnum;
import com.mycom.SpringAWSLearning.enums.RolesEnum;
import com.mycom.SpringAWSLearning.utils.UserUtils;

public abstract class AbstractServiceIntegrationTest {
	
	@Autowired
	protected UserService userService;
	
	protected User CreateUser(TestName testName) {
		String username = testName.getMethodName();
		String email = testName.getMethodName()+"@gmail.com";
		Set<UserRole> userRoles = new HashSet<>();
		User basicUser = UserUtils.CreateBasicUser(username, email); 
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
		User user = userService.CreateUser(basicUser, PlansEnum.BASIC, userRoles);
		return user;
	}

}

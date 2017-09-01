package com.mycom.SpringAWSLearning.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Plan;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Role;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.UserRole;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.PlanRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.RoleRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.UserRepository;
import com.mycom.SpringAWSLearning.enums.PlansEnum;
import com.mycom.SpringAWSLearning.enums.RolesEnum;
import com.mycom.SpringAWSLearning.utils.UserUtils;

public abstract class AbstractIntegrationTest {

	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected PlanRepository planRepository;
	
	protected Role CreateRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}
	
	protected Plan CreatePlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}
	
	protected User CreateUser(String username, String email)
	{
		
		Plan basicPlan = CreatePlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		
		User basicUser = UserUtils.CreateBasicUser(username, email);
		basicUser.setPlan(basicPlan);
		
		Role basicRole = CreateRole(RolesEnum.BASIC);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser,basicRole);
	
		
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		
		userRepository.save(basicUser);
		return basicUser;
	}
	
	protected User CreateUser(TestName testname)
	{
		return CreateUser(testname.getMethodName(),testname.getMethodName()+"@gmail.com");
	}
	
}

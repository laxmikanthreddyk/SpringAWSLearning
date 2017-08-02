package com.mycom.SpringAWSLearning.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


import com.mycom.SpringAWSLearning.SpringAwsLearningApplication;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Plan;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Role;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.UserRole;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.PlanRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.RoleRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.UserRepository;
import com.mycom.SpringAWSLearning.enums.PlansEnum;
import com.mycom.SpringAWSLearning.enums.RolesEnum;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class RepositoriesIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	private static final int BASIC_USER_ID = 1;
	
	@Before
	public void init()
	{
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(userRepository);
		Assert.assertNotNull(roleRepository);
	}
	
	@Test
	public void testCreateNewPlan() throws Exception
	{
		Plan basicPlan = CreatePlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan);
	}

	private Plan CreatePlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}
	

	@Test
	public void testCreateNewRole() throws Exception
	{
		Role basicrole = CreateRole(RolesEnum.BASIC);
		roleRepository.save(basicrole);
		Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}

	private Role CreateRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}
	
	
	private User CreateBasicUser()
	{
		User user = new User();
		user.setUsername("laxmikanth");
		user.setCountry("US");
		user.setEmail("laxmikanthcode@gmail.com");
		user.setEnabled(true);
		user.setFirstName("Laxmikanth");
		user.setLastName("Koduru");
		user.setPhoneNumber("5516895560");
		user.setDescription("First User Account");
		user.setPassword("secret");
		user.setProfileImageUrl("http://test.com");
		
		return user;
	}
	
	@Test
	public void CreateUserTest() throws Exception
	{
		Plan basicPlan = CreatePlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		
		User basicUser = CreateBasicUser();
		basicUser.setPlan(basicPlan);
		
		Role basicRole = CreateRole(RolesEnum.BASIC);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser,basicRole);
	
		
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		for(UserRole ur : userRoles)
		{
			roleRepository.save(ur.getRole());
		}
		
		userRepository.save(basicUser);
		
		User newCreatedUser = userRepository.findOne(basicUser.getId());
		Assert.assertNotNull(newCreatedUser);
		Assert.assertTrue(newCreatedUser.getId()!=0);
		Assert.assertNotNull(newCreatedUser.getPlan());
		Assert.assertNotNull(newCreatedUser.getPlan().getId());
		Set<UserRole> newUserRoles = newCreatedUser.getUserRoles();
		for(UserRole ur:newUserRoles)
		{
			Assert.assertNotNull(ur.getRole());
			Assert.assertNotNull(ur.getRole().getId());
		}
		
	}

}

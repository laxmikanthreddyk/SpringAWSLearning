package com.mycom.SpringAWSLearning.test.integration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
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
import com.mycom.SpringAWSLearning.utils.UserUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=SpringAwsLearningApplication.class)
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {
	
	
	@Rule public TestName testName = new TestName();
	
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
	
	@Test
	public void testCreateNewRole() throws Exception
	{
		Role basicrole = CreateRole(RolesEnum.BASIC);
		roleRepository.save(basicrole);
		Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}
	
	@Test
	public void CreateUserTest() throws Exception
	{
		String username = testName.getMethodName();
		String email = testName.getMethodName()+"@gmail.com";
		
		User basicUser = CreateUser(username, email);
		
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
	
	@Test
	public void DeleteUserTest() throws Exception
	{
		String username = testName.getMethodName();
		String email = testName.getMethodName()+"@gmail.com";
		User basicUser = CreateUser(username, email);
		userRepository.delete(basicUser.getId());
	}
	
	@Test
	public void testGetUserByEmail() throws Exception
	{
		User user = CreateUser(testName);
		
		User foundUserByEmail = userRepository.findByEmail(user.getEmail());
		Assert.assertNotNull(foundUserByEmail);
		Assert.assertNotNull(foundUserByEmail.getId());
		
	}
	
	@Test
	public void testUpdateUserPassword() throws Exception
	{
		User user = CreateUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		
		String password = UUID.randomUUID().toString();
		userRepository.updateUserPassword(user.getId(), password);
		
		User modifiedUser= userRepository.findOne(user.getId());
		
		Assert.assertEquals(password, modifiedUser.getPassword());
    }
}

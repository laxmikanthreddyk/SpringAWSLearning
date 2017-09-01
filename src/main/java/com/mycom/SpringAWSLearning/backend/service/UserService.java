package com.mycom.SpringAWSLearning.backend.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Plan;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;
import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.UserRole;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.PlanRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.RoleRepository;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.UserRepository;
import com.mycom.SpringAWSLearning.enums.PlansEnum;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public User CreateUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Plan plan = new Plan(plansEnum);
		if(!planRepository.exists(plansEnum.getId()))
		{
			plan = planRepository.save(plan);
		}
		user.setPlan(plan);
		for(UserRole ur: userRoles)
		{
			roleRepository.save(ur.getRole());
		}
		user.getUserRoles().addAll(userRoles);
		user = userRepository.save(user);
		return user;
	}
	
	@Transactional
	public void updateUserPassword(long userId, String password)
	{
		password = passwordEncoder.encode(password);
		userRepository.updateUserPassword(userId, password);
		LOG.debug("Password updated Successfully for userId {}",userId);
	}
	
	@Transactional
	public User getUserDetails(long userId)
	{
		return userRepository.findOne(userId);
	}
	
	public User findByUserName(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
}

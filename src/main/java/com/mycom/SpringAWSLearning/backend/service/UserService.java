package com.mycom.SpringAWSLearning.backend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional
	public User CreateUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles)
	{
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
}
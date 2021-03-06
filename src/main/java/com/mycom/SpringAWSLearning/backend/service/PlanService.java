package com.mycom.SpringAWSLearning.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.Plan;
import com.mycom.SpringAWSLearning.backend.persistence.repositories.PlanRepository;
import com.mycom.SpringAWSLearning.enums.PlansEnum;

@Service
@Transactional(readOnly=true)
public class PlanService {
	
	@Autowired
	private PlanRepository planRepository;
	
	public Plan findPlanById(int planId)
	{
		return planRepository.findOne(planId);
	}

	@Transactional
	public Plan CreatePlan(int planId)
	{
		Plan plan = null;
		
		if(planId ==1)
		{
			plan = planRepository.save(new Plan(PlansEnum.BASIC));
		}
		else if(planId ==2)
		{
			plan = planRepository.save(new Plan(PlansEnum.PRO));
		}
		else
		{
			throw new IllegalArgumentException("Plan id "+planId+ " not recognized");
		}
		return plan;
	}
}

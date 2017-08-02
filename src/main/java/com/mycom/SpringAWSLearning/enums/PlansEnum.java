package com.mycom.SpringAWSLearning.enums;

public enum PlansEnum {
	
	BASIC(1,"Basic"),
	PRO(2,"Pro");
	
	private final int id;
	
	private String planName;

    PlansEnum(int id, String planName) {
		this.id = id;
		this.planName = planName;
	}

	public String getPlanName() {
		return planName;
	}


	public int getId() {
		return id;
	}
	
	

}

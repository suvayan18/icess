package com.icess.model;

public class CustomCandidateSkill {

	private String isDeleted;
	private String candidateskillId;
	private String skillId;
	private String subskillId;
	private String skillName;
	private String subSkillName;
	
	
	
	public String getCandidateskillId() {
		return candidateskillId;
	}
	public void setCandidateskillId(String candidateskillId) {
		this.candidateskillId = candidateskillId;
	}
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	public String getSubskillId() {
		return subskillId;
	}
	public void setSubskillId(String subskillId) {
		this.subskillId = subskillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getSubSkillName() {
		return subSkillName;
	}
	public void setSubSkillName(String subSkillName) {
		this.subSkillName = subSkillName;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}

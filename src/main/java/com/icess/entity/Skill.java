package com.icess.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="icess_skill")
public class Skill {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "skill_id", unique = true, nullable = false)
	private int skillId;	
	
	
	@Column(name = "skill")
	private String skill;
	
	@OneToMany(
	        mappedBy = "skillnames", 
	        cascade = CascadeType.ALL)
	    private List<SubSkill> subskills = new ArrayList<>();

	
	
	@OneToMany(
	        mappedBy = "userskill", 
	        cascade = CascadeType.ALL
	    )
	    private List<Question> questions = new ArrayList<>();


	public List<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}


	public int getSkillId() {
		return skillId;
	}


	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}


	public String getSkill() {
		return skill;
	}


	public void setSkill(String skill) {
		this.skill = skill;
	}


	public List<SubSkill> getSubskills() {
		return subskills;
	}


	public void setSubskills(List<SubSkill> subskills) {
		this.subskills = subskills;
	}


	
			
			
	
	
}

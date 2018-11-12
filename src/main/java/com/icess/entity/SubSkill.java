package com.icess.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="icess_subskill")
public class SubSkill {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "subskill_id", unique = true, nullable = false)
	private int subSkillId;	
	
	@Column(name = "subskill")
	private String subSkill;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skillnames;
	
	@OneToMany(
	        mappedBy = "usersubskill", 
	        cascade = CascadeType.ALL
	    )
	    private List<Question> questions = new ArrayList<>();


	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getSubSkillId() {
		return subSkillId;
	}

	public void setSubSkillId(int subSkillId) {
		this.subSkillId = subSkillId;
	}

	public String getSubSkill() {
		return subSkill;
	}

	public void setSubSkill(String subSkill) {
		this.subSkill = subSkill;
	}

	public Skill getSkillnames() {
		return skillnames;
	}

	public void setSkillnames(Skill skillnames) {
		this.skillnames = skillnames;
	}

	
	
	
	
}

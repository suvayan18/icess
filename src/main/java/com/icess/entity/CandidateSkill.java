package com.icess.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="icess_candidate_skill")
public class CandidateSkill {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "candidate_skill_id", unique = true, nullable = false)
	private int candidateskillId;	
	
	@Column(name = "skill_id")
	private int skillId;
	
	@Column(name = "subskill_id")
	private int subskillId;
	
	@Column(name = "skill_name")
	private String skillName;
	
	@Column(name = "sub_skill_name")
	private String subSkillName;
	
	@Column(name = "is_deleted")
	private String isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateid")
    private Candidate candidate;

	public int getCandidateskillId() {
		return candidateskillId;
	}

	public void setCandidateskillId(int candidateskillId) {
		this.candidateskillId = candidateskillId;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getSubskillId() {
		return subskillId;
	}

	public void setSubskillId(int subskillId) {
		this.subskillId = subskillId;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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

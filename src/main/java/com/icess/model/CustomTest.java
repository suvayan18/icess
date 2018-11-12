package com.icess.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.icess.entity.Candidate;
import com.icess.entity.CandidateSkill;
import com.icess.entity.Test;
import com.icess.entity.User;

public class CustomTest {

	private int candidateId;
	
	private String candidateName;
	
	private String email;
	
	private String address;
	
	private String contact_No1;
	
	private String contact_No2;
	
	private List<CandidateSkill> candidateskills;
	
    private int testId;		
	
	private String testCode;	
	
	private Date testDate;
	
	private int marks;
	
	private String isPassed;

	
	
	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public List<CandidateSkill> getCandidateskills() {
		return candidateskills;
	}

	public void setCandidateskills(List<CandidateSkill> candidateskills) {
		this.candidateskills = candidateskills;
	}

	public String getContact_No1() {
		return contact_No1;
	}

	public void setContact_No1(String contact_No1) {
		this.contact_No1 = contact_No1;
	}

	public String getContact_No2() {
		return contact_No2;
	}

	public void setContact_No2(String contact_No2) {
		this.contact_No2 = contact_No2;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public String getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	
	
	
	
}

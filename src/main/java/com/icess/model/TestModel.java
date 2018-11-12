package com.icess.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.icess.entity.Candidate;

public class TestModel {

private int testId;		
	
	private String testCode;	
	
	private Date testDate;	
	
	private String startTime;	
	
	private String endTime;	
	
	private Candidate candidate;
	
	private List<QuestionModel> qustionModelList;

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public List<QuestionModel> getQustionModelList() {
		return qustionModelList;
	}

	public void setQustionModelList(List<QuestionModel> qustionModelList) {
		this.qustionModelList = qustionModelList;
	}
	
	
	
	
	
}

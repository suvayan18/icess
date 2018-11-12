package com.icess.entity;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="icess_test")
public class Test {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "test_id", unique = true, nullable = false)
	private int testId;		
	
	@Column(name = "test_code")
	private String testCode;	
	
	@Column(name = "test_date")
	private Date testDate;	
	
	@Column(name = "start_time")
	private String startTime;	
	
	@Column(name = "end_time")
	private String endTime;	
	
	@Column(name = "final_score")
	private String finalScore;	
	
	@Column(name = "test_status")
	private String testStatus;	
	
	@Column(name = "test_flag")
	private Boolean istestFlag;	
	
	@OneToOne
	@JoinColumn(name="candidate_id")
	private Candidate candidate;

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

	

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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

	public Boolean getIstestFlag() {
		return istestFlag;
	}

	public void setIstestFlag(Boolean istestFlag) {
		this.istestFlag = istestFlag;
	}
	
	
	
	
	
	
	
	
	
}

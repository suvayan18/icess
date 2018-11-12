package com.icess.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="icess_marks")
public class Marks {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "marks_id", unique = true, nullable = false)
	private int marksId;	
	
	@Column(name="marks")
	private int marks;
	
	@Column(name="is_passed")
	private String isPassed;
	
	@OneToOne
	@JoinColumn(name="candidate_id")
	private Candidate candidate ;
	
	
	@OneToOne
	@JoinColumn(name="test_id")
	private Test test;


	public int getMarksId() {
		return marksId;
	}


	public void setMarksId(int marksId) {
		this.marksId = marksId;
	}


	public Candidate getCandidate() {
		return candidate;
	}


	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}


	public Test getTest() {
		return test;
	}


	public void setTest(Test test) {
		this.test = test;
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

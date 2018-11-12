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
@Table(name="icess_test_answer")
public class Answer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "test_answer_id", unique = true, nullable = false)
	private int answerId;		
	
	@Column(name = "desc_answer")
	private String descAnswer;	
	
	@Column(name = "score")
	private int answerScore;
	
	
	@Column(name = "save_status")
	private String saveStatus;
	
	
//	@Column(name = "evaluation_marks")
//	private String evaluationMarks;
	
	@Column(name = "remarks")
	private String remarks;
	
	@OneToOne
	@JoinColumn(name="test_id")
	private Test test;
	
	@OneToOne
	@JoinColumn(name="question_id")
	private Question question;
	
//	@Column(name = "answer_flag")
//	private Boolean isanswerFlag;	

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getDescAnswer() {
		return descAnswer;
	}

	public void setDescAnswer(String descAnswer) {
		this.descAnswer = descAnswer;
	}

	public int getAnswerScore() {
		return answerScore;
	}

	public void setAnswerScore(int answerScore) {
		this.answerScore = answerScore;
	}

	public String getSaveStatus() {
		return saveStatus;
	}

	public void setSaveStatus(String saveStatus) {
		this.saveStatus = saveStatus;
	}



	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
	
}

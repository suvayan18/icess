package com.icess.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="icess_test_answer_mcq")
public class AnswerMcq {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "test_answer_mcq_id", unique = true, nullable = false)
	private int answermcqId;
	
	
	@Column(name = "mcq_answer")
	private Boolean mcqAnswer;	
	
//	@Column(name = "answer_mcq_flag")
//	private Boolean isanswermcqFlag;	
//	

	
//	@OneToOne
//	@JoinColumn(name="test_answer_id")
//	private Answer answer;
	
//	@OneToOne
//	@JoinColumn(name="question_id")
//	private Question question;
	
	@OneToOne
	@JoinColumn(name="question_mcq_id")
	private QuestionMcq questionmcq;
	
	@OneToOne
	@JoinColumn(name="test_id")
	private Test test;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="question_id")
	private Question question;

	public int getAnswermcqId() {
		return answermcqId;
	}

	public void setAnswermcqId(int answermcqId) {
		this.answermcqId = answermcqId;
	}


	public Boolean getMcqAnswer() {
		return mcqAnswer;
	}

	public void setMcqAnswer(Boolean mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
	}

	public QuestionMcq getQuestionmcq() {
		return questionmcq;
	}

	public void setQuestionmcq(QuestionMcq questionmcq) {
		this.questionmcq = questionmcq;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}


	
	
	
	
}

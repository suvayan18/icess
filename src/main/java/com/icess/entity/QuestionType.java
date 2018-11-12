package com.icess.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="icess_question_type")
public class QuestionType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "question_type_id", unique = true, nullable = false)
	private int questionTypeId;
	
	@Column(name = "question_type")
	private String questionType;

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	
	
	
	
}

package com.icess.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.icess.entity.QuestionMcq;
import com.icess.entity.QuestionType;

public class QuestionModel {

	
private int questionId;		
	
private String questionCode;
	
private String questionText;
	
private int questionPoint;
	
private int questionTypeId;

private String descAnswer;

private int answerId;

private List<McqAnwerModel> mcqAnswerModelList;


public int getQuestionId() {
	return questionId;
}


public void setQuestionId(int questionId) {
	this.questionId = questionId;
}


public String getQuestionCode() {
	return questionCode;
}


public void setQuestionCode(String questionCode) {
	this.questionCode = questionCode;
}


public String getQuestionText() {
	return questionText;
}


public void setQuestionText(String questionText) {
	this.questionText = questionText;
}


public int getQuestionPoint() {
	return questionPoint;
}


public void setQuestionPoint(int questionPoint) {
	this.questionPoint = questionPoint;
}


public int getQuestionTypeId() {
	return questionTypeId;
}


public void setQuestionTypeId(int questionTypeId) {
	this.questionTypeId = questionTypeId;
}


public List<McqAnwerModel> getMcqAnswerModelList() {
	return mcqAnswerModelList;
}


public void setMcqAnswerModelList(List<McqAnwerModel> mcqAnswerModelList) {
	this.mcqAnswerModelList = mcqAnswerModelList;
}


public String getDescAnswer() {
	return descAnswer;
}


public void setDescAnswer(String descAnswer) {
	this.descAnswer = descAnswer;
}


public int getAnswerId() {
	return answerId;
}


public void setAnswerId(int answerId) {
	this.answerId = answerId;
}




	
}

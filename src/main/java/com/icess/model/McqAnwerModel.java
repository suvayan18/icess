package com.icess.model;

import javax.persistence.Column;

public class McqAnwerModel {

private int questionId;
	
private int questionmcqId;	
	
private String questionoptions;	
	
private Boolean correctOption;

//private int givenAnswer;

private int answermcqId;

private Boolean mcqAnswer;

public int getQuestionId() {
	return questionId;
}

public void setQuestionId(int questionId) {
	this.questionId = questionId;
}

public int getQuestionmcqId() {
	return questionmcqId;
}

public void setQuestionmcqId(int questionmcqId) {
	this.questionmcqId = questionmcqId;
}

public String getQuestionoptions() {
	return questionoptions;
}

public void setQuestionoptions(String questionoptions) {
	this.questionoptions = questionoptions;
}

public Boolean getCorrectOption() {
	return correctOption;
}

public void setCorrectOption(Boolean correctOption) {
	this.correctOption = correctOption;
}

//public int getGivenAnswer() {
//	return givenAnswer;
//}
//
//public void setGivenAnswer(int givenAnswer) {
//	this.givenAnswer = givenAnswer;
//}
//
//public int getAnswerMcqId() {
//	return answerMcqId;
//}
//
//public void setAnswerMcqId(int answerMcqId) {
//	this.answerMcqId = answerMcqId;
//}

public Boolean getMcqAnswer() {
	return mcqAnswer;
}

public void setMcqAnswer(Boolean mcqAnswer) {
	this.mcqAnswer = mcqAnswer;
}

public int getAnswermcqId() {
	return answermcqId;
}

public void setAnswermcqId(int answermcqId) {
	this.answermcqId = answermcqId;
}





	
}

package com.icess.model;

import java.util.List;

import com.icess.entity.Answer;
import com.icess.entity.AnswerMcq;
import com.icess.entity.QuestionMcq;

public class RequestAnswer {
	
//	List<AnswerMcq> listanswerMcq;
	//List<Answer> listanswer;
	
	
	private int testId;	
	private int questionId;
	private Boolean isMcq;
    private List<QuestionMcq> listmcq;
    private String descAnswer;
    private String endTime;
    
    
    
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public Boolean getIsMcq() {
		return isMcq;
	}
	public void setIsMcq(Boolean isMcq) {
		this.isMcq = isMcq;
	}
	public List<QuestionMcq> getListmcq() {
		return listmcq;
	}
	public void setListmcq(List<QuestionMcq> listmcq) {
		this.listmcq = listmcq;
	}
	public String getDescAnswer() {
		return descAnswer;
	}
	public void setDescAnswer(String descAnswer) {
		this.descAnswer = descAnswer;
	}
	
	
	

}

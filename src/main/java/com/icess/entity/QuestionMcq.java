package com.icess.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="icess_question_mcq")
public class QuestionMcq {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "question_mcq_id", unique = true, nullable = false)
	private int questionmcqId;	
	
	@Column(name = "questionoptions")
	//@ElementCollection
	private String questionoptions;
	
	@Column(name = "correctoption")
	//@ElementCollection
	private Boolean correctOption;
	
	
	 @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "question_id")
     private Question questionsop;

	

      public Question getQuestionsop() {
		return questionsop;
	}

   public void setQuestionsop(Question questionsop) {
		this.questionsop = questionsop;
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





	

}

package com.icess.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="icess_question")
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "question_id", unique = true, nullable = false)
	private int questionId;		
	
	@Column(name = "question_code")
	private String questionCode;
	
	@Column(name = "question_text")
	private String questionText;
	
	@Column(name = "question_point")
	private int questionPoint;
	
	@OneToOne
	@JoinColumn(name="question_type_id")
	private QuestionType questiontype;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill userskill;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subskill_id")
    private SubSkill usersubskill;
	
	@OneToMany(
	        mappedBy = "questionsop", 
	        cascade = CascadeType.ALL)
	    private List<QuestionMcq> questionmcq = new ArrayList<>();
		
	
//	
//	public Question(int questionId) {
//		super();
//		this.questionId = questionId;
//	}

	public Skill getUserskill() {
		return userskill;
	}

	public void setUserskill(Skill userskill) {
		this.userskill = userskill;
	}

	public SubSkill getUsersubskill() {
		return usersubskill;
	}

	public void setUsersubskill(SubSkill usersubskill) {
		this.usersubskill = usersubskill;
	}

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

	public QuestionType getQuestiontype() {
		return questiontype;
	}

	public void setQuestiontype(QuestionType questiontype) {
		this.questiontype = questiontype;
	}

	public List<QuestionMcq> getQuestionmcq() {
		return questionmcq;
	}

	public void setQuestionmcq(List<QuestionMcq> questionmcq) {
		this.questionmcq = questionmcq;
	}

	public int getQuestionPoint() {
		return questionPoint;
	}

	public void setQuestionPoint(int questionPoint) {
		this.questionPoint = questionPoint;
	}


	
	

	
	
}

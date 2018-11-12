package com.icess.service;

import java.util.List;
import java.util.concurrent.Future;

import com.icess.entity.Question;
import com.icess.entity.QuestionMcq;
import com.icess.entity.Skill;
import com.icess.entity.SubSkill;

public interface QuestionService {

	
	public Future<Boolean>  addQuestion(Question question);
	
	public Future<List<Question>> viewQuestion(int skillId,int subskillId);	
	
	public Future<List<QuestionMcq>> viewQuestionOptionById(int questionId);
	
	public Future<Question> ViewQuestionById(int questionId);
	
	public Future<List<Skill>> getAllSkill();
	
	public Future<List<SubSkill>> getSubSkillBySkillId(int skillId);
	
	public Future<Skill> getSkillById(int skillId);
	
	public Future<List<SubSkill>> getAllSubSkill();
	
	public Future<List<Question>> getAllQuestion();
	
	
	
	
	
}

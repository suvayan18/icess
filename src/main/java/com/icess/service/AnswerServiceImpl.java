package com.icess.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jinq.tuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icess.JinqSource;
import com.icess.entity.AnswerMcq;
import com.icess.entity.Question;
import com.icess.entity.QuestionMcq;
import com.icess.entity.Skill;
import com.icess.entity.SubSkill;

@Component("AnswerService")
@Transactional
public class AnswerServiceImpl implements AnswerService{

	@PersistenceContext
	private EntityManager em;
		
	@Autowired
	private JinqSource source;	
	
	
//	 public Future<Boolean> addAnswer(Question answermcq){
//	  
//		 try {
//				List<Pair<Question,QuestionMcq>> lq= source.streamAll(em, Question.class)
//			   .join((tt,source)->source.stream(QuestionMcq.class))
//			   .where(tt->tt.getOne().getQuestionId()==tt.getTwo().getQuestionsop().getQuestionId()
//			   && tt.getOne().getQuestiontype().getQuestionTypeId()==1)
//			   .toList();
//						
//				List<QuestionMcq> mcqList = new ArrayList<QuestionMcq>();
//				
//				lq.iterator().next().getOne().getQuestionmcq().forEach(f->{
//					QuestionMcq mcq = new QuestionMcq();
//					mcq.setQuestionmcqId(f.getQuestionmcqId());
//					mcq.setQuestionoptions(f.getQuestionoptions());
//					mcq.setCorrectOption(f.getCorrectOption());
//					
//					mcqList.add(mcq);
//				});
//				
//				Question question = new Question();
//				for(Pair<Question,QuestionMcq> tt : lq) {
//					question.setQuestionId(tt.getOne().getQuestionId());
//					question.setQuestionCode(tt.getOne().getQuestionCode());
//					question.setQuestionText(tt.getOne().getQuestionText());
//					question.setQuestiontype(tt.getOne().getQuestiontype());
//					question.setQuestionPoint(tt.getOne().getQuestionPoint());
//					question.setUserskill(new Skill() {{
//						setSkillId(tt.getOne().getUserskill().getSkillId());
//						setSkill(tt.getOne().getUserskill().getSkill());
//					}});
//					question.setUsersubskill(new SubSkill() {{
//						setSubSkillId(tt.getOne().getUsersubskill().getSubSkillId());
//						setSubSkill(tt.getOne().getUsersubskill().getSubSkill());				
//					}});
//					question.setQuestionmcq(mcqList);
//				}
//				return new AsyncResult<Boolean>(true);
//				
//			}
//			catch(Exception ex)
//			{
//				System.out.print(ex.getMessage());
//			}	 
//		 return null;
//		 
//	 }
	
	
	
}

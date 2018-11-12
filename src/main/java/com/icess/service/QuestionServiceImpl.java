package com.icess.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jinq.tuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icess.JinqSource;
import com.icess.entity.Question;
import com.icess.entity.QuestionMcq;
import com.icess.entity.Skill;
import com.icess.entity.SubSkill;
import com.icess.repository.QuestionMcqRepository;
import com.icess.repository.QuestionRepository;

@Component("QuestionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

@PersistenceContext
private EntityManager em;
	
@Autowired
private JinqSource source;

@Autowired
private QuestionRepository questionRepository;	

@Autowired
private QuestionMcqRepository questionmcqRepository;



public Future<Boolean> addQuestion(Question question) {
	   
		try{
			 Question qes = questionRepository.saveAndFlush(question);
				System.out.println("QuestionTypeId = "+qes.getQuestiontype().getQuestionTypeId());
			   //if(!qes.getQuestionmcq().get(0).getQuestionoptions().equals(null)) {
				if(qes.getQuestiontype().getQuestionTypeId()==1) {
				   System.out.println("IN BETWEEN IF....");
				   List<QuestionMcq> lqm = source.streamAll(em, QuestionMcq.class)
						   .where(ss->ss.getQuestionsop().equals(null))
						   .toList();
				   
					for (int i=0;i<question.getQuestionmcq().size();i++) {
						QuestionMcq mcq=lqm.get(i);
						mcq.setQuestionsop(qes);
						questionmcqRepository.saveAndFlush(mcq);
					}
					
				}		   
			   return new AsyncResult<Boolean>(true);
			
			
		}catch(Exception ex)
		{
			System.out.print(ex.getMessage());
			return new AsyncResult<Boolean>(false);
		}
		//return null;
	     
	   
}

	
   public List<QuestionMcq> getQuestionMcqList(int questionId){
	   try {
		   List<QuestionMcq> qml = source.streamAll(em, QuestionMcq.class)
			   .where(s->s.getQuestionsop().getQuestionId() == questionId)
			   .map(o-> new QuestionMcq() {{
				  //setQuestionmcqId(o.getQuestionmcqId());
				   setQuestionoptions(o.getQuestionoptions());
				   setCorrectOption(o.getCorrectOption());
				   setQuestionsop(o.getQuestionsop());
			   }})
			   .collect(Collectors.toList());
		   System.out.println("QuestionId = "+qml.get(0).getQuestionsop().getQuestionId());
		   return qml;
	   }catch(Exception e) {
		   e.printStackTrace();
		   return null;
	   }
   }

	public Future<List<Question>> viewQuestion(int skillId,int subskillId) {
		try{
		
		List<Question> lq= source.streamAll(em, Question.class)	
		.where(tt->tt.getUserskill().getSkillId()==skillId 
		&& tt.getUsersubskill().getSubSkillId()==subskillId)
		.sortedBy(o-> o.getQuestionId())
		.map(oo->new Question() {{
			setQuestionId(oo.getQuestionId());
			setQuestionText(oo.getQuestionText());
			setQuestionCode(oo.getQuestionCode());
			setQuestiontype(oo.getQuestiontype());
			setQuestionPoint(oo.getQuestionPoint());
			setUserskill(new Skill() {{
				setSkillId(oo.getUserskill().getSkillId());
				setSkill(oo.getUserskill().getSkill());
			}});
			setUsersubskill(new SubSkill() {{
				setSubSkillId(oo.getUsersubskill().getSubSkillId());
				setSubSkill(oo.getUsersubskill().getSubSkill());
				
			}});
			setQuestionmcq(QuestionOption(oo.getQuestionId()));
			
		}})
		.collect(Collectors.toList());
		
			
		return new AsyncResult<List<Question>>(lq);
		
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
		
		
		return null;
	}
	
	public Future<List<QuestionMcq>> viewQuestionOptionById(int questionId) {
		
		try{
			
			List<QuestionMcq> lmcq= source.streamAll(em, QuestionMcq.class)
					.where(qm->qm.getQuestionsop().getQuestionId()==questionId)
					.map(mc->new QuestionMcq() {{
					setQuestionoptions(mc.getQuestionoptions());
					setCorrectOption(mc.getCorrectOption());
					setQuestionmcqId(mc.getQuestionmcqId());
					}})
					.collect(Collectors.toList());
		
			return new AsyncResult<List<QuestionMcq>>(lmcq);	
		}
		
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}		
		return null;		
	}
	
	
public List<QuestionMcq> QuestionOption(int questionId) {
				
			List<QuestionMcq> lmcq= source.streamAll(em, QuestionMcq.class)
					.where(qm->qm.getQuestionsop().getQuestionId()==questionId)
					.map(mc->new QuestionMcq() {{
					setQuestionoptions(mc.getQuestionoptions());
					setCorrectOption(mc.getCorrectOption());
					setQuestionmcqId(mc.getQuestionmcqId());
					}})
					.collect(Collectors.toList());
		
			return lmcq;		
	}
	

	
	public Future<Question> ViewQuestionById(int questionId) {
		
		try {
			List<Pair<Question,QuestionMcq>> lq= source.streamAll(em, Question.class)
		   .join((tt,source)->source.stream(QuestionMcq.class))
		   .where(tt->tt.getOne().getQuestionId()==tt.getTwo().getQuestionsop().getQuestionId()
		   && tt.getOne().getQuestionId()==questionId)
		   .toList();
			
			
			Question ab= source.streamAll(em, Question.class)
			.where(mm->mm.getQuestionId()==questionId)
			.map(s->new Question() {{
				setQuestionId(s.getQuestionId());
				setQuestionText(s.getQuestionText());
				setQuestiontype(s.getQuestiontype());
				setQuestionPoint(s.getQuestionPoint());
				setQuestionCode(s.getQuestionCode());
				setUserskill(new Skill() {{
					setSkillId(s.getUserskill().getSkillId());
					setSkill(s.getUserskill().getSkill());
				}});
				setUsersubskill(new SubSkill() {{
					setSubSkillId(s.getUsersubskill().getSubSkillId());
					setSubSkill(s.getUsersubskill().getSubSkill());				
				}});
			}})
			.collect(Collectors.toList()).get(0);
				
			if(ab.getQuestiontype().getQuestionTypeId()==1) {
			List<QuestionMcq> mcqList = new ArrayList<QuestionMcq>();
			lq.iterator().next().getOne().getQuestionmcq().forEach(f->{
				QuestionMcq mcq = new QuestionMcq();
				mcq.setQuestionmcqId(f.getQuestionmcqId());
				mcq.setQuestionoptions(f.getQuestionoptions());
				mcq.setCorrectOption(f.getCorrectOption());
				
				mcqList.add(mcq);
			});
			
			
			
			Question question = new Question();
			for(Pair<Question,QuestionMcq> tt : lq) {
				question.setQuestionId(tt.getOne().getQuestionId());
				question.setQuestionCode(tt.getOne().getQuestionCode());
				question.setQuestionText(tt.getOne().getQuestionText());
				question.setQuestiontype(tt.getOne().getQuestiontype());
				question.setQuestionPoint(tt.getOne().getQuestionPoint());
				question.setUserskill(new Skill() {{
					setSkillId(tt.getOne().getUserskill().getSkillId());
					setSkill(tt.getOne().getUserskill().getSkill());
				}});
				question.setUsersubskill(new SubSkill() {{
					setSubSkillId(tt.getOne().getUsersubskill().getSubSkillId());
					setSubSkill(tt.getOne().getUsersubskill().getSubSkill());				
				}});
				question.setQuestionmcq(mcqList);
			}
			
			
			
			return new AsyncResult<Question>(question);
			}
			else{
				return new AsyncResult<Question>(ab);
				
			}
				
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}			
		return null;
	}	
	
	
	public Future<List<Skill>> getAllSkill(){
	
		try {
			List<Skill> skl= source.streamAll(em, Skill.class)
		    .map(ss->new Skill() {{
		    	setSkill(ss.getSkill());
		    	setSkillId(ss.getSkillId());
		    }})				
		    .collect(Collectors.toList());
			
			return new AsyncResult<List<Skill>>(skl);
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}	
	
		return null;
		
	}
	
	
	
	public Future<Skill> getSkillById(int skillId){
		
		try {
			Skill skl= source.streamAll(em, Skill.class)
			.where(ss->ss.getSkillId()==skillId)		
		    .map(ss->new Skill() {{
		    	setSkill(ss.getSkill());
		    	setSkillId(ss.getSkillId());
		    }})				
		    .collect(Collectors.toList()).get(0);
			
			return new AsyncResult<Skill>(skl);
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}		
		return null;	
		
	}
	
	
	public Future<List<SubSkill>> getSubSkillBySkillId(int skillId){
		try {
			List<SubSkill> skl= source.streamAll(em, SubSkill.class)
			.where(ss->ss.getSkillnames().getSkillId()==skillId)
			.map(pp->new SubSkill() {{
				setSubSkill(pp.getSubSkill());
				setSubSkillId(pp.getSubSkillId());
                setSkillnames(new Skill() {{
                	setSkillId(pp.getSkillnames().getSkillId());
                	setSkill(pp.getSkillnames().getSkill());
                }});
			}})
			.collect(Collectors.toList());	
			return new AsyncResult<List<SubSkill>>(skl);
		}		
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}	
		return null;
	
	}
	
	public Future<List<SubSkill>> getAllSubSkill(){
		try {
			List<SubSkill> skl= source.streamAll(em, SubSkill.class)
					.map(pp->new SubSkill() {{
						setSubSkill(pp.getSubSkill());
						setSubSkillId(pp.getSubSkillId());
		                setSkillnames(new Skill() {{
		                	setSkillId(pp.getSkillnames().getSkillId());
		                	setSkill(pp.getSkillnames().getSkill());
		                }});
					}})
					.collect(Collectors.toList());	
					return new AsyncResult<List<SubSkill>>(skl);
			
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}	
		return null;
	}
	
	
	
	public Future<List<Question>> getAllQuestion(){
		
		 try {
			 List<Question> qList = source.streamAll(em, Question.class)
		    .where(tt->tt.getQuestiontype().getQuestionTypeId()==1) 		 
			.map(oo->new Question() {{
				setQuestionId(oo.getQuestionId());
				setQuestionPoint(oo.getQuestionPoint());
				setQuestionText(oo.getQuestionText());
				setQuestionCode(oo.getQuestionCode());
				setUserskill(new Skill() {{
					setSkillId(oo.getUserskill().getSkillId());
					setSkill(oo.getUserskill().getSkill());
				}});
				setUsersubskill(new SubSkill() {{
					setSubSkillId(oo.getUsersubskill().getSubSkillId());
					setSubSkill(oo.getUsersubskill().getSubSkill());
					
				}});
				setQuestionmcq(QuestionOption(oo.getQuestionId()));
			}})	 
			.collect(Collectors.toList());
		    	
		 return new AsyncResult<List<Question>>(qList);
		 }	
			
			catch(Exception ex)
			{
				System.out.print(ex.getMessage());
			}	 
		
		
		return null;
	}
	
	
	
}

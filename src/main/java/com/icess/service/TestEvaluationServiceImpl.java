package com.icess.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jinq.jpa.JPAJinqStream;
import org.jinq.tuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icess.JinqSource;
import com.icess.entity.Answer;
import com.icess.entity.AnswerMcq;
import com.icess.entity.Candidate;
import com.icess.entity.CandidateSkill;
import com.icess.entity.Marks;
import com.icess.entity.Question;
import com.icess.entity.QuestionMcq;
import com.icess.entity.Skill;
import com.icess.entity.SubSkill;
import com.icess.entity.Test;
import com.icess.entity.User;
import com.icess.model.McqAnwerModel;
import com.icess.model.QuestionModel;
import com.icess.model.TestModel;
import com.icess.repository.AnswerMcqRepository;
import com.icess.repository.AnswerRepository;
import com.icess.repository.MarksRepository;
import com.icess.repository.TestRepository;

@Component("TestEvaluationService")
@Transactional
public class TestEvaluationServiceImpl implements TestEvaluationService {
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@Autowired
	private JinqSource source;
	
	@Resource
	private AnswerRepository answerRepository;
	
	@Autowired
	private AnswerMcqRepository answerMcqRepository;
	
	@Resource
	private TestRepository testRepository;
	
	@Resource
	private MarksRepository marksRepository;
	
	@Resource
	private Environment environment;

	public Future<List<Test>> getAllPendingTest() {
		
		try {
			 List<Test> tList = source.streamAll(entityManager, Test.class)
			.where(tt->tt.getIstestFlag() == false)
			.map(pp->new Test() {{
				setTestId(pp.getTestId());
				//setCandidate(pp.getCandidate());
				setTestStatus(pp.getTestStatus());
				setIstestFlag(pp.getIstestFlag());
			}})
			.collect(Collectors.toList());
			 return new AsyncResult<List<Test>>(tList);
			
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
		
	}
	
	
	
	public Future<Integer> testResultByTestId(int testId , List<Answer> ansList) {		
		try {
				int sum=0;			
			List<Integer> questionIdList = source.streamAll(entityManager, AnswerMcq.class)
								.where(t->t.getTest().getTestId() == testId)
								.select(s-> s.getQuestion().getQuestionId())
								.distinct()
								.collect(Collectors.toList());
			
			for(Integer questionId : questionIdList) {
				sum += getMarks(questionId , testId);
			} 
			
			/*List<Answer> ansList = source.streamAll(em, Answer.class)
						.where(t->t.getTest().getTestId() == testId)
						.map(o-> new Answer() {{
							setAnswerId(o.getAnswerId());
							setAnswerScore(o.getAnswerScore());
						}})
						.collect(Collectors.toList());*/
			
			for(Answer answer : ansList) {
				sum += answer.getAnswerScore();
				//answer.setIsanswerFlag(true);
				answerRepository.saveAndFlush(answer);
			}
			
			/*List<AnswerMcq> answerMcqList = source.streamAll(entityManager, AnswerMcq.class)
					.where(t->t.getTest().getTestId() == testId)
					.map(s-> new AnswerMcq() {{
						setAnswermcqId(s.getAnswermcqId());
						setMcqAnswer(s.getMcqAnswer());
						setQuestion(new Question() {{
							setQuestionId(s.getQuestion().getQuestionId());
						}});
						setQuestionmcq(new QuestionMcq() {{
							setQuestionmcqId(s.getQuestionmcq().getQuestionmcqId());
						}});
						setTest(new Test() {{
							setTestId(s.getTest().getTestId());
						}});
						setMcqFlagAnswer(s.getMcqFlagAnswer());
					}})
					.collect(Collectors.toList());
			
			for(AnswerMcq answerMcq : answerMcqList) {
				System.out.println("answerMcq Id = "+answerMcq.getAnswermcqId());
				answerMcq.setMcqFlagAnswer("Y");
				//answerMcqTemp.setIsanswermcqFlag(true);
				answerMcqRepository.saveAndFlush(answerMcq);
			}*/
			
			Test test = source.streamAll(entityManager, Test.class)
					.where(t->t.getTestId() == testId)
					.collect(Collectors.toList()).get(0);
					//.getOnlyValue();
			
			test.setIstestFlag(true);
			testRepository.saveAndFlush(test);
			
			int passMarks = Integer.parseInt(environment.getRequiredProperty("test.passMarks"));
			Marks marks = new Marks();
			marks.setTest(test);
			marks.setCandidate(test.getCandidate());
			marks.setMarks(sum);
			marks.setIsPassed(sum >= passMarks ? "Y" : "N");
			marksRepository.saveAndFlush(marks);
			
			return new AsyncResult<Integer>(sum);
			
		}catch (Exception e) {
			System.out.print(e.getMessage());
			 return new AsyncResult<Integer>(0);
		}
				
	}
	
	
	public int getMarks(int questionId , int testId) {
		try {			
			List<AnswerMcq> answerMcqList = source.streamAll(entityManager, AnswerMcq.class)
					.where(t->t.getTest().getTestId() == testId && 
							  t.getQuestionmcq().getQuestionsop().getQuestionId() == questionId)
					.map(s-> new AnswerMcq() {{
						setAnswermcqId(s.getAnswermcqId());
						setMcqAnswer(s.getMcqAnswer());
						setQuestion(new Question() {{
							setQuestionId(s.getQuestion().getQuestionId());
						}});
						setQuestionmcq(new QuestionMcq() {{
							setQuestionmcqId(s.getQuestionmcq().getQuestionmcqId());
						}});
						setTest(new Test() {{
							setTestId(s.getTest().getTestId());
						}});
					}})
					.collect(Collectors.toList());
				
			List<QuestionMcq> questionMcqList = source.streamAll(entityManager, QuestionMcq.class)
							  .where(s-> s.getQuestionsop().getQuestionId() == questionId &&
									  	 s.getCorrectOption() == true)
							  .map(o-> new QuestionMcq() {{
								  setQuestionmcqId(o.getQuestionmcqId());
								  setCorrectOption(o.getCorrectOption());
								  setQuestionsop(new Question() {{
									  setQuestionId(o.getQuestionsop().getQuestionId());
								  }});
							  }})
							  .collect(Collectors.toList());
			
			if(questionMcqList.size() == answerMcqList.size()) {
				for(int i=0;i<questionMcqList.size();i++) {
					QuestionMcq questionMcq = questionMcqList.get(i);
					AnswerMcq answerMcq = answerMcqList.get(i);
					
					if(questionMcq.getQuestionmcqId() != answerMcq.getQuestionmcq().getQuestionmcqId()) {
						return 0;
					}
				}	
			}else {
				return 0;
			}
			int marks = source.streamAll(entityManager, Question.class)
					.where(s-> s.getQuestionId() == questionId)
					.findFirst().get().getQuestionPoint();

					
			return marks;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public Future<List<Answer>> getallAnswerbytestId(int testId) {
		
		try {
			 List<Answer> tList = source.streamAll(entityManager, Answer.class)
			 .where(tt->tt.getTest().getTestId()==testId)
			 .map(p->new Answer() {{
			 setAnswerId(p.getAnswerId());
			 setDescAnswer(p.getDescAnswer());
			 setAnswerScore(p.getAnswerScore());
			 setTest(new Test() {{
				 setTestId(p.getTest().getTestId());
			 }});
			 setQuestion(new Question() {{
				 setQuestionId(p.getQuestion().getQuestionId());
				 setQuestionText(p.getQuestion().getQuestionText());
			 }});
			 }})
			.collect(Collectors.toList());
			 return new AsyncResult<List<Answer>>(tList);
			
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
		
	}
	
	public Future<TestModel> viewQuestionAnswer(int testId){
		try {
			
			TestModel testModel =source.streamAll(entityManager, Test.class)
			    .where(m->m.getTestId()==testId)
			    .map(p->new TestModel() {{
			    	setTestId(p.getTestId());
			    	setTestCode(p.getTestCode());
			    	setStartTime(p.getStartTime());
			    	setEndTime(p.getEndTime());
			    	setTestDate(p.getTestDate());
			    	setCandidate(new Candidate() {{
			    		 setCandidateId(p.getCandidate().getCandidateId());
						  setCandidateName(p.getCandidate().getCandidateName());
						  setAddress(p.getCandidate().getAddress());
						  setContact_No1(p.getCandidate().getContact_No1());
						  setContact_No2(p.getCandidate().getContact_No2());
						  setEmail(p.getCandidate().getEmail());
						  setReg_date(p.getCandidate().getReg_date());
						  setUser(new User() {{
							  setUserId(p.getCandidate().getUser().getUserId());
						  }});
						  setCandidateskills(getCandidateSubSkillList(p.getCandidate().getCandidateId()));
						  setCandidatestatusFlag(p.getCandidate().getCandidatestatusFlag());
						  setQustionModelList(getAllQuestionList(testId));
			    	}});
			    }})
			   .collect(Collectors.toList()).get(0);
					
			return new AsyncResult<TestModel>(testModel);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public List<QuestionModel> getAllQuestionList(int testId) {
		try{
		
		List<QuestionModel> lq= source.streamAll(entityManager, Question.class)
		.join((pp,source)->source.stream(Answer.class))
		.where(mm->mm.getTwo().getQuestion().getQuestionId()==mm.getOne().getQuestionId()
		      && mm.getTwo().getTest().getTestId()==testId && mm.getOne().getQuestiontype().getQuestionTypeId()==2)
		.map(qq->new QuestionModel() {{
		   setQuestionId(qq.getOne().getQuestionId());
		   setQuestionText(qq.getOne().getQuestionText());
			setQuestionPoint(qq.getOne().getQuestionPoint());
			setQuestionTypeId(qq.getOne().getQuestiontype().getQuestionTypeId());
			setDescAnswer(qq.getTwo().getDescAnswer());
			setAnswerId(qq.getTwo().getAnswerId());
		}}).collect(Collectors.toList());
		
		
		
		/*List<QuestionModel> mcql= source.streamAll(entityManager, Question.class)
				.join((pp,source)->source.stream(AnswerMcq.class))
				.where(mm->mm.getTwo().getQuestion().getQuestionId()==mm.getOne().getQuestionId()
				      && mm.getTwo().getTest().getTestId()==testId && mm.getOne().getQuestiontype().getQuestionTypeId()==1)
				 .map(qq->new QuestionModel() {{
					 setQuestionId(qq.getOne().getQuestionId());
					   setQuestionText(qq.getOne().getQuestionText());
						setQuestionPoint(qq.getOne().getQuestionPoint());
						setQuestionTypeId(qq.getOne().getQuestiontype().getQuestionTypeId());
						setMcqAnswerModelList(getAllMcqList(qq.getOne().getQuestionId()));
				 }})
				 .collect(Collectors.toList());*/
		
		List<QuestionModel> mcql= new ArrayList<QuestionModel>();
		
		List<Integer> questionIdList = source.streamAll(entityManager, AnswerMcq.class)
				.where(t->t.getTest().getTestId() == testId && t.getQuestion().getQuestiontype().getQuestionTypeId()==1)
				.select(s-> s.getQuestion().getQuestionId())
				.distinct()
				.collect(Collectors.toList());

		for(Integer questionId : questionIdList) {
			QuestionModel questionModel = source.streamAll(entityManager, Question.class)
					.where(t-> t.getQuestionId() == questionId)
					.map(o-> new QuestionModel() {{
						setQuestionId(o.getQuestionId());
						   setQuestionText(o.getQuestionText());
							setQuestionPoint(o.getQuestionPoint());
							setQuestionTypeId(o.getQuestiontype().getQuestionTypeId());
							setMcqAnswerModelList(getAllMcqList(o.getQuestionId()));
					}})
					.collect(Collectors.toList()).get(0);
			mcql.add(questionModel);
		} 

		  List<QuestionModel> mergeList= new ArrayList<QuestionModel>();
		  mergeList.addAll(lq);
		  mergeList.addAll(mcql);
		
		  List<QuestionModel> sortedList = mergeList.stream()
				  							.sorted((a,b)->String.valueOf(a.getQuestionId()).compareTo(String.valueOf(b.getQuestionId())))
				  							.collect(Collectors.toList());

		return(sortedList);	
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
		
		return null;
	}
	
	
	public List<McqAnwerModel> getAllMcqList(int questionId) {
		try {
			
			String query="SELECT iqm.question_id, iqm.question_mcq_id, iqm.correctoption, iqm.questionoptions, itam.mcq_answer, itam.test_answer_mcq_id "
					+"FROM icess_question_mcq iqm LEFT JOIN icess_test_answer_mcq itam "
					+"ON iqm.question_mcq_id=itam.question_mcq_id "
					+"WHERE iqm.question_id =:questionId";
			
			Query mcqAns = entityManager.createNativeQuery(query);
			mcqAns.setParameter("questionId", questionId);		
			
			List<Object[]> obj=mcqAns.getResultList();
			
			List<McqAnwerModel> mcqList= new ArrayList<McqAnwerModel>();
			for(Object[] object : obj) {
				McqAnwerModel mcqAnwerModel = new McqAnwerModel();
				mcqAnwerModel.setQuestionId(Integer.parseInt(object[0].toString()));
				mcqAnwerModel.setQuestionmcqId(Integer.parseInt(object[1].toString()));
				mcqAnwerModel.setCorrectOption(((Boolean)object[2]).booleanValue());
				mcqAnwerModel.setQuestionoptions(object[3].toString());
				mcqAnwerModel.setMcqAnswer(object[4] != null ? ((Boolean)object[4]).booleanValue() : Boolean.valueOf("0"));
				//mcqAnwerModel.setAnswermcqId(Integer.parseInt(object[5].toString()));
				
				mcqList.add(mcqAnwerModel);
			}
			/*List<McqAnwerModel> mcqList= source.streamAll(entityManager, QuestionMcq.class)
			.leftOuterJoin((qm,source)->source.stream(AnswerMcq.class),
							(qm,am)->qm.getQuestionmcqId() == am.getQuestionmcq().getQuestionmcqId())
			.where(kk->kk.getOne().getQuestionsop().getQuestionId()==questionId)
			.map(nn->new McqAnwerModel() {{
				setQuestionoptions(nn.getOne().getQuestionoptions());
				setCorrectOption(nn.getOne().getCorrectOption());
				setQuestionmcqId(nn.getOne().getQuestionmcqId());
				//setAnswerMcqId(nn.getTwo().getAnswermcqId());
				//setGivenAnswer(nn.getTwo().getQuestionmcq().getQuestionmcqId());
				setMcqAnswer(nn.getTwo().getMcqAnswer() == null ? false : true);
		
			}})
			.collect(Collectors.toList());*/	
			
			return(mcqList);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CandidateSkill> getCandidateSubSkillList(int candidateId){
		try{
			List<CandidateSkill> candidateSkillList = source.streamAll(entityManager, CandidateSkill.class)
				.join((tt,source)->source.stream(Skill.class))
				.where(tt->tt.getOne().getSkillId() == tt.getTwo().getSkillId() &&
						   tt.getOne().getIsDeleted().equals("N"))
				.join((tt,source)->source.stream(SubSkill.class))
				.where(tt->tt.getOne().getOne().getSubskillId() == tt.getTwo().getSubSkillId() 
						&& tt.getOne().getOne().getCandidate().getCandidateId() == candidateId)
				
				.map(ss->new CandidateSkill() {{
					setCandidateskillId(ss.getOne().getOne().getCandidateskillId());
					setSkillName(ss.getOne().getTwo().getSkill());
					setSkillId(ss.getOne().getOne().getSkillId());
					setSubskillId(ss.getOne().getOne().getSubskillId());
					setSubSkillName(ss.getTwo().getSubSkill());
					setIsDeleted(ss.getOne().getOne().getIsDeleted());
				}})
				.collect(Collectors.toList());
			return candidateSkillList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public HashMap<String,Object> getCandidateResult(int testId) {
		Marks marks = source.streamAll(entityManager, Marks.class)
				.where(t-> t.getTest().getTestId() == testId)
				.map(o-> new Marks(){{
					setMarks(o.getMarks());
					setIsPassed(o.getIsPassed());
					setCandidate(new Candidate() {{
						setCandidateId(o.getCandidate().getCandidateId());
						setCandidateName(o.getCandidate().getCandidateName() );;
					}});
				}})
				.collect(Collectors.toList()).get(0);
		
		Integer questionPoint = getQuestionPoint(testId);
		 HashMap<String,Object> hm1=new HashMap<String,Object>();
		 hm1.put("CandidateId", marks.getCandidate().getCandidateId());
		 hm1.put("CandidateName", marks.getCandidate().getCandidateName());
		 hm1.put("isPassed",marks.getIsPassed());
		 hm1.put("marks",marks.getMarks());
		 hm1.put("questionPoint", questionPoint);
		return hm1;

	}
	
	public Integer getQuestionPoint(int testId) {
		int sum = 0; 
		try {
			List<QuestionModel> questionModelList = getAllQuestionList(testId);
			
			for(QuestionModel questionModel : questionModelList) {
				//System.out.println("QuestionPoint = "+questionModel.getQuestionPoint());
				sum += questionModel.getQuestionPoint();
			}
			return sum;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
		
}

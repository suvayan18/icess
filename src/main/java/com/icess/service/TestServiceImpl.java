package com.icess.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icess.entity.Answer;
import com.icess.entity.AnswerMcq;
import com.icess.entity.Candidate;
import com.icess.entity.Question;
import com.icess.entity.QuestionMcq;
import com.icess.model.RequestAnswer;
import com.icess.entity.Test;
import com.icess.repository.AnswerRepository;
import com.icess.repository.CandidateRepository;
import com.icess.repository.TestRepository;
import com.icess.repository.AnswerMcqRepository;

@Component("TestService")
@Transactional
public class TestServiceImpl implements TestService {
	
	@Autowired
	TestRepository testRepository;
	
	@Autowired
	AnswerMcqRepository answerMcqRespository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Resource
	private CandidateRepository candidateRepository;
	
	@Resource
	private Environment environment;

	@Override
	public Test startTest(Test test) {
		try {
			//String  testduration =environment.getRequiredProperty("test.duration");
			//String time = "15:30:18";
			String time = test.getStartTime();

			DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date date = sdf.parse(time);

			System.out.println("Time: " + sdf.format(date));
			Date session_expiry = new Date(date.getTime() + 60 * 60 * 2000);
			System.out.println("Time: " + sdf.format(session_expiry));
			test.setEndTime(String.valueOf(sdf.format(session_expiry)));
			test.setIstestFlag(false);
			Test obj=testRepository.save(test);
			Candidate cn=candidateRepository.findByCandidateId(test.getCandidate().getCandidateId());
			cn.setCandidatestatusFlag(true);
			candidateRepository.saveAndFlush(cn);
			return obj;
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
//	public Test startTest(Test test) {
//		try {
//			Test obj=testRepository.save(test);
//			Candidate cn=candidateRepository.findByCandidateId(test.getCandidate().getCandidateId());
//			cn.setCandidatestatusFlag(true);
//			candidateRepository.saveAndFlush(cn);
//			
//			return obj;
//		}catch(Exception e) {
//			System.out.print(e.getMessage());
//		}
//		return null;
//	}

	

//	@Override
//	public RequestAnswer answerTest(RequestAnswer answer) {
//	
//		try {
//			if(answer.getListanswerMcq().size()!=0) {
//				for(int i=0;i<answer.getListanswerMcq().size();i++) {
//					answer.getListanswerMcq().get(i).setIsanswermcqFlag(false);
//					answerMcqRespository.saveAndFlush(answer.getListanswerMcq().get(i));	
//				}
//				
//			}
//			if(answer.getListanswer().size()!=0) {
//				for(int i=0;i<answer.getListanswer().size();i++) {
//					answer.getListanswer().get(i).setIsanswerFlag(false);
//					Answer	answerdesc = answerRepository.saveAndFlush(answer.getListanswer().get(i));	
//				}
//			}
//			
//			return answer;
//			
//		}catch(Exception e) {
//			System.out.print(e.getMessage());
//		}
//		
//		return null;
//	}
//	
	
	@Override
public List<RequestAnswer> answerTest(List<RequestAnswer> answer) {
		
		
		try {
			
			for(int i=0; i<answer.size();i++) {
				System.out.println("IsMcq = "+answer.get(i).getIsMcq());
				if(answer.get(i).getIsMcq()) {
					
					for(int j=0;j<answer.get(i).getListmcq().size();j++) {
						AnswerMcq mcq=new AnswerMcq();
						Question ques= new Question();
						Test test= new Test();
						ques.setQuestionId(answer.get(i).getQuestionId());
						mcq.setQuestion(ques);
						test.setTestId(answer.get(i).getTestId());
						mcq.setTest(test);
						QuestionMcq mcqques = new QuestionMcq();
						mcqques.setQuestionmcqId(answer.get(i).getListmcq().get(j).getQuestionmcqId());
						mcqques.setCorrectOption(answer.get(i).getListmcq().get(j).getCorrectOption());
						mcq.setMcqAnswer(true);
						mcq.setQuestionmcq(mcqques);
						
						//mcq.setIsanswermcqFlag(false);
						AnswerMcq m=answerMcqRespository.saveAndFlush(mcq);
						System.out.println(m.getAnswermcqId());
					}
					
				}else {
					AnswerMcq mcq=new AnswerMcq();
					Question ques= new Question();
					Test test= new Test();
					Answer descriptive= new Answer();
					descriptive.setDescAnswer(answer.get(i).getDescAnswer());
					ques.setQuestionId(answer.get(i).getQuestionId());
					test.setTestId(answer.get(i).getTestId());
					descriptive.setQuestion(ques);
					descriptive.setTest(test);
					//descriptive.setIsanswerFlag(false);
					Answer des=	answerRepository.saveAndFlush(descriptive);
					System.out.println(des.getAnswerId());
				}
			}
			
			Test test1= testRepository.findByTestId(answer.get(0).getTestId());
			test1.setTestId(answer.get(0).getTestId());
			test1.setEndTime(answer.get(0).getEndTime());
			test1.setTestStatus("finish");
			testRepository.saveAndFlush(test1);
			return answer;
			
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		
		return null;
	}
//	public List<RequestAnswer> answerTest(List<RequestAnswer> answer) {
//		
//		
//		try {
//			
//			for(int i=0; i<answer.size();i++) {
//				if(answer.get(i).getIsMcq()) {
//					
//					for(int j=0;j<answer.get(i).getListmcq().size();j++) {
//						AnswerMcq mcq=new AnswerMcq();
//						Question ques= new Question();
//						Test test= new Test();
//						ques.setQuestionId(answer.get(i).getQuestionId());
//						mcq.setQuestion(ques);
//						test.setTestId(answer.get(i).getTestId());
//						mcq.setTest(test);
//						QuestionMcq mcqques = new QuestionMcq();
//						mcqques.setQuestionmcqId(answer.get(i).getListmcq().get(j).getQuestionmcqId());
//						mcq.setQuestionmcq(mcqques);
//						mcq.setMcqAnswer(true);
//						//mcq.setMcqAnswer(answer.get(i).getListmcq().get(j).getCorrectOption());
//						//mcq.setIsanswermcqFlag(false);
//						AnswerMcq m=answerMcqRespository.saveAndFlush(mcq);
//						System.out.println(m.getAnswermcqId());
//					}
//					
//				}else {
//					AnswerMcq mcq=new AnswerMcq();
//					Question ques= new Question();
//					Test test= new Test();
//					Answer descriptive= new Answer();
//					descriptive.setDescAnswer(answer.get(i).getDescAnswer());
//					ques.setQuestionId(answer.get(i).getQuestionId());
//					test.setTestId(answer.get(i).getTestId());
//					descriptive.setQuestion(ques);
//					descriptive.setTest(test);
//					//descriptive.setIsanswerFlag(false);
//					Answer des=	answerRepository.saveAndFlush(descriptive);
//					System.out.println(des.getAnswerId());
//				}
//			}
//			
//			
//			/*if(answer.getListanswerMcq().size()!=0) {
//				for(int i=0;i<answer.getListanswerMcq().size();i++) {
//					answerMcqRespository.saveAndFlush(answer.getListanswerMcq().get(i));	
//				}
//				
//			}
//			if(answer.getListanswer().size()!=0) {
//				for(int i=0;i<answer.getListanswer().size();i++) {
//					Answer	answerdesc = answerRepository.saveAndFlush(answer.getListanswer().get(i));	
//				}
//			}*/
//			/*Test test1= new Test();
//			test1.setTestId(answer.get(0).getTestId());
//			test1.setEndTime(answer.get(0).getEndTime());
//			testRepository.saveAndFlush(test1);*/
//			return answer;
//			
//		}catch(Exception e) {
//			System.out.print(e.getMessage());
//		}
//		
//		return null;
//	}	
//	
	

	
	
	
}

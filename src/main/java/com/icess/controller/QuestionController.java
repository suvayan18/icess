package com.icess.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icess.entity.Question;
import com.icess.entity.QuestionMcq;
import com.icess.entity.Skill;
import com.icess.entity.SubSkill;
import com.icess.entity.User;
import com.icess.repository.QuestionMcqRepository;
import com.icess.repository.QuestionRepository;
import com.icess.repository.SkillRepository;
import com.icess.service.LoginService;
import com.icess.service.QuestionService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;	
	
	@Autowired
	private QuestionMcqRepository questionmcqRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private SkillRepository skillRepository;
	
	
	
	@RequestMapping(value = "/addQuestions", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> questionAdd(@RequestBody Question ques) throws InterruptedException, ExecutionException{	
		
		
		Future<Boolean> returnValue=questionService.addQuestion(ques);

		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
					
	}
	
//	@RequestMapping(value = "/editQuestions", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
//	public  ResponseEntity<Object> questionEdit(@RequestBody Question ques) throws InterruptedException, ExecutionException{	
//		
//		
//		Future<Boolean> returnValue=questionService.editQuestion(ques);
//
//		if(returnValue.get()==null){
//            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
//					
//	}
	
	
	
	@RequestMapping(value = "/viewQuestions", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> questionView(@RequestParam int skilId,@RequestParam int subskillId) throws InterruptedException, ExecutionException{
		
		Future<List<Question>> list=questionService.viewQuestion(skilId, subskillId);

		
		if(list.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(list.get() , HttpStatus.OK);
			
		
	}	
	
	
	@RequestMapping(value = "/viewQuestionOptions", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> questionOptionView(@RequestParam int questionId) throws InterruptedException, ExecutionException{
		
		Future<List<QuestionMcq>> list=questionService.viewQuestionOptionById(questionId);
		
		if(list.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(list.get() , HttpStatus.OK);
			
		
	}	
	
	
	
	@RequestMapping(value = "/viewQuestionById/{questionId}", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> questionOptionById(@PathVariable int questionId) throws InterruptedException, ExecutionException{
		
		Future<Question> ob=questionService.ViewQuestionById(questionId);
		
		if(ob.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(ob.get() , HttpStatus.OK);
			
		
	}
	
	@RequestMapping(value = "/getAllQuestions", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<?> getAllQuestion() throws InterruptedException, ExecutionException{
		
		Future<List<Question>> list=questionService.getAllQuestion();

		
		if(list.get()==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list.get() , HttpStatus.OK);
			
		
	}	
			
	
}

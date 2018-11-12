package com.icess.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icess.entity.Answer;
import com.icess.entity.Marks;
import com.icess.entity.Question;
import com.icess.entity.Test;
import com.icess.model.TestModel;
import com.icess.service.TestEvaluationService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess")
public class TestEvaluationController {

	
	@Autowired
	private TestEvaluationService testEvaluationService;
	
	@RequestMapping(value = "/getAllPendingTest", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<?> getAllPendingTest() throws InterruptedException, ExecutionException{
		
		Future<List<Test>> list=testEvaluationService.getAllPendingTest();

		
		if(list.get()==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list.get() , HttpStatus.OK);
			
		
	}
	
	
	@RequestMapping(value = "/getallAnswerbytestId", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> getallAnswerbytestId(@RequestParam int testId) throws InterruptedException, ExecutionException{	
		
		
		Future<List<Answer>> returnValue=testEvaluationService.getallAnswerbytestId(testId);

		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
					
	}
	

	@RequestMapping(value = "/testResult", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> questionAdd(@RequestBody List<Answer> ansList) throws InterruptedException, ExecutionException{	
		
		int testId=ansList.get(0).getTest().getTestId();
		Future<Integer> returnValue= testEvaluationService.testResultByTestId(testId , ansList);

		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
					
	}
	
	
	@RequestMapping(value = "/viewQuestionAnswer", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> viewQuestionAnswer(@RequestParam int testId) throws InterruptedException, ExecutionException{	
		
		
		Future <TestModel> returnValue=testEvaluationService.viewQuestionAnswer(testId);

		if(returnValue.get()==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue.get() , HttpStatus.OK);
					
	}
	
	@RequestMapping(value = "/candidateResult", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<Object> candidateResult(@RequestParam int testId) throws InterruptedException, ExecutionException{	
		
		
		HashMap<String,Object> returnValue=testEvaluationService.getCandidateResult(testId);

		if(returnValue==null){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(returnValue , HttpStatus.OK);
					
	}
	
	
}

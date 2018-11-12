package com.icess.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.icess.entity.AnswerMcq;
import com.icess.model.RequestAnswer;
import com.icess.entity.Test;
import com.icess.model.TestStartResponse;
import com.icess.repository.TestRepository;
import com.icess.service.TestService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess/test")
public class TestController {
	
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private TestService testService;
	
	
	@RequestMapping(value = "/startTest", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<?> startTest(@RequestBody Test test) {
		
	  Test obj=	testService.startTest(test);
	  HashMap<String,Object> hm=new HashMap<String,Object>();
		
		if(obj!=null) {
			TestStartResponse response= new TestStartResponse();
			response.setCandidateId(String.valueOf(obj.getCandidate().getCandidateId()));
			response.setEndTime(obj.getEndTime());
			response.setStartTime(obj.getStartTime());
			response.setTestCode(obj.getTestCode());
			response.setTestId(String.valueOf(obj.getTestId()));
			response.setTestStatus(obj.getTestStatus());
			response.setFinalScore(obj.getFinalScore());
			response.setTestDate(String.valueOf(obj.getTestDate()));
			hm.put("data", response);			
			return new ResponseEntity<>(hm,HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(hm,HttpStatus.NOT_ACCEPTABLE);	
		}
	}
	
	
	@RequestMapping(value = "/answerTest", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
	public ResponseEntity<?> answerTest(@RequestBody List<RequestAnswer> requestAnswer) {
		
		List<RequestAnswer> obj=	testService.answerTest(requestAnswer);
	    HashMap<String,Object> hm=new HashMap<String,Object>();
		
		if(obj!=null) {
			hm.put("data", obj);			
			return new ResponseEntity<>(hm,HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(hm,HttpStatus.NOT_ACCEPTABLE);	
		}
	}
	

}

package com.icess.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.icess.entity.Skill;
import com.icess.service.QuestionService;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess")
public class SkillController {

	
	@Autowired
	private QuestionService questionService;
	
	
	
	@RequestMapping(value = "/getAllSkill", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<?> getSkills() throws InterruptedException, ExecutionException{
		
	//	List<Skill> list=skillRepository.findAll();
		
		Future<List<Skill>> list= questionService.getAllSkill(); 
		 
		if(list.get()==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list.get() , HttpStatus.OK);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

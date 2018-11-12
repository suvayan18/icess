package com.icess.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.icess.entity.SubSkill;
import com.icess.service.QuestionService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess")
public class SubSkillController {

	@Autowired
	private QuestionService questionService;
	
	
	@RequestMapping(value = "/getSubSkillBySkillId/{skillId}", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<?> getSubSkills(@PathVariable int skillId) throws InterruptedException, ExecutionException{
		
	//	List<Skill> list=skillRepository.findAll();
		
		Future<List<SubSkill>> list= questionService.getSubSkillBySkillId(skillId); 
		 
		if(list.get()==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list.get() , HttpStatus.OK);
		
	}	
	
	
	@RequestMapping(value = "/getAllSubSkill", method = RequestMethod.GET,headers = "Accept=application/json;charset=UTF-8")
	public  ResponseEntity<?> getAllSubSkills() throws InterruptedException, ExecutionException{
		
	//	List<Skill> list=skillRepository.findAll();
		
		Future<List<SubSkill>> list= questionService.getAllSubSkill(); 
		 
		if(list.get()==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list.get() , HttpStatus.OK);
		
	}
	
		
	
}

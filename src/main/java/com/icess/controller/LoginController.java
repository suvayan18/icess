package com.icess.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.icess.entity.Candidate;
import com.icess.entity.User;
import com.icess.repository.CandidateRepository;
import com.icess.service.LoginService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/icess")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
//	public  ResponseEntity<?> login(@Validated @RequestBody User user) {
//		
//		User ur = loginService.loginCheck(user);
//		 HashMap<String,Object> hm=new HashMap<String,Object>();  
//		  
//		if (ur!= null) {
//			hm.put("data", ur);			
//			return new ResponseEntity<>(hm,HttpStatus.OK);
//		}
//		else{
//			hm.put("error", "Not Found");
//			return new ResponseEntity<>(hm,HttpStatus.NOT_ACCEPTABLE);
//			
//		}
//				
//
//	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,headers = "Accept=application/json;charset=UTF-8")
public  ResponseEntity<?> login(@Validated @RequestBody User user) {

  User ur = loginService.loginCheck(user);
  HashMap<String,Object> hm=new HashMap<String,Object>();
  if(ur!=null) {
    if(ur.getRole().getRoleId()==1) {
   Candidate cn=candidateRepository.findByUserUserId(ur.getUserId());
    HashMap<String,Object> hm1=new HashMap<String,Object>();
    hm1.put("candidateId", cn.getCandidateId());
    hm.put("data", ur);	
    hm.put("candidate",hm1);
    return new ResponseEntity<>(hm,HttpStatus.OK);
   }
    else {
   hm.put("data", ur);			
   return new ResponseEntity<>(hm,HttpStatus.OK);
}
} 
  else {
  hm.put("error", "Not Found");
  return new ResponseEntity<>(hm,HttpStatus.NOT_ACCEPTABLE);
}
  
}
	
	
	
	
	
	
	
}

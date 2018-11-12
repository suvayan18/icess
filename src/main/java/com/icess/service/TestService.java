package com.icess.service;



import com.icess.model.RequestAnswer;

import java.util.List;

import com.icess.entity.Test;

public interface TestService {

	public Test startTest(Test test);
//	public RequestAnswer answerTest(RequestAnswer requestAnswer);
	
	public List<RequestAnswer> answerTest(List<RequestAnswer> answer);
	
}

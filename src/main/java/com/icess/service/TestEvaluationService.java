package com.icess.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import com.icess.entity.Answer;
import com.icess.entity.Marks;
import com.icess.entity.Test;
import com.icess.model.TestModel;

public interface TestEvaluationService {
	
	public Future<List<Test>> getAllPendingTest();
	
	public Future<Integer> testResultByTestId(int testId , List<Answer> ansList);
	
	public Future<List<Answer>> getallAnswerbytestId(int testId);
	
	public Future<TestModel> viewQuestionAnswer(int testId);
	
	public HashMap<String,Object> getCandidateResult(int testId);
	
}

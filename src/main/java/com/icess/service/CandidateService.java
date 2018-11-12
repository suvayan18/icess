package com.icess.service;

import java.util.List;
import java.util.concurrent.Future;

import com.icess.entity.Candidate;
import com.icess.model.CustomTest;

public interface CandidateService {

	//public Future<Boolean> addEditCandidate(Candidate candidate);
	
	public Future<Candidate> addEditCandidate(Candidate candidate);
	
	public Future<Candidate> getCandidateById(int candidateId);
	
	public Future<List<Candidate>> getAllCandidate();
	
	public Future<List<Candidate>> getAllPendingTestCandidate();
	
	//public Future<List<Candidate>> getAllPendingEvaluationTestCandidate();
	
	public Future<List<CustomTest>> getAllPendingEvaluationTestCandidate();
	
	public Future<List<Candidate>> getCandidateSearch(int skill , int subSkill , String name , String contactNo);
	
	
	public Future<List<CustomTest>> getAllEvaluatedCandidate();
	
}

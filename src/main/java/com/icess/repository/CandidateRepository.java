package com.icess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icess.entity.Candidate;
import com.icess.entity.User;

public interface CandidateRepository extends JpaRepository<Candidate , Integer> {
	
	Candidate findByCandidateId(int candidateId);
	
	Candidate findByUserUserId(Long userId);
}

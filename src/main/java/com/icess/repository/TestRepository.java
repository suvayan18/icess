package com.icess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icess.entity.Test;


public interface TestRepository extends JpaRepository<Test,Integer> {

Test findByTestId(int testId);
	
}

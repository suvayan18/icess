package com.icess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icess.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

}

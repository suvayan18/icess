package com.icess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icess.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findByUserNameAndPassword(String email,String password);
	
}

package com.icess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icess.entity.User;
import com.icess.repository.UserRepository;

@Component("LoginService")
@Transactional
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private UserRepository userRepository ;
	
	public User loginCheck(User user) {
		User ob=new User();
		try{
		String username = user.getUserName();
		String password=user.getPassword();
		
		//String pwd = MD5.ConvertToMD5(user.getPassword());
     	ob= userRepository.findByUserNameAndPassword(username,password);
		
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
		
		
		return ob;
	}

}

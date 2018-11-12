package com.icess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;



@SpringBootApplication

public class IcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcessApplication.class, args);
	}


	@Bean(name = "messageSource")
	 public ReloadableResourceBundleMessageSource messageSource() {
  ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
     messageBundle.setBasename("classpath:messages/messages");
	  messageBundle.setDefaultEncoding("UTF-8");
	  return messageBundle;
	  }	
	
	
	
	

}



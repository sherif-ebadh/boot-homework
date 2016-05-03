package io.fourfinanceit.homework.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.User;
import io.fourfinanceit.homework.model.LoanRequest;
import io.fourfinanceit.homework.model.UserRequest;
import io.fourfinanceit.homework.service.LoanService;

@RestController
public class LoanController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/", method = GET)
	public ResponseEntity<String> index() {
		return new ResponseEntity<String>("Welcome to 4financeit! :) Please use the correct Request", HttpStatus.OK);
	}

	@Autowired
	private LoanService loanService;
	
	
	@RequestMapping(value="/user",method=POST)
	public ResponseEntity<String> user( @RequestBody UserRequest userReq) {
		logger.info("Start create User method");
				User user = loanService.createUser(userReq);
		if(user == null){
			logger.info("UserNot Created for username" + userReq.getUserName() );
			return new ResponseEntity<String>("User Not created", HttpStatus.NOT_ACCEPTABLE);
		}
		logger.info("User created with username " + user.getUserName());
		return new ResponseEntity<String>("User created with username " + user.getUserName(), HttpStatus.CREATED);
	}
		
	@RequestMapping(value="/user/{userName}/loan",method=POST)
	public ResponseEntity<String>  loan(@PathVariable String userName, @RequestBody LoanRequest loanReq, HttpServletRequest request)
	{
		logger.info("Start Create a new Loan request");
		User user = loanService.checkUser(userName);
		if(user == null){
			logger.info("rejection user " + userName +" Not Found");
			return new ResponseEntity<String>("Rejection No user Found with username " + userName , HttpStatus.NOT_FOUND);
		}
	
		Loan loan = loanService.saveLoan(loanReq,userName,request,user);
		if(loan == null){
			logger.info("Loan not created for user " + userName );
			return new ResponseEntity<String>("Rejection", HttpStatus.NOT_ACCEPTABLE);
			
		} 
		logger.info("New Loan Created for user " + userName + " with loan Refernce " + loan.getId());
		return new ResponseEntity<String>("New Loan Created for user " + userName + " with loan Refernce ID " + loan.getId(), HttpStatus.CREATED);
			
	}

}

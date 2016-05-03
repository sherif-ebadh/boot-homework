package io.fourfinanceit.homework.service;
 
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.fourfinanceit.homework.dao.LoanDAO;
import io.fourfinanceit.homework.dao.UserDAO;
import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.User;
import io.fourfinanceit.homework.model.LoanRequest;
import io.fourfinanceit.homework.model.UserRequest;
 
@Configuration
@PropertySource("classpath:config.properties")
@Component
@Service
public class LoanService {
     
	@Autowired
    private LoanDAO loanDao;
	
	
	@Autowired
    private UserDAO userDao;
	
	public User checkUser(String userName) {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			return null;
		}
		return user;
	}
	
	
	public User createUser(UserRequest userReq) {
		logger.info("Create new User Method");
		User user = new User(userReq.getFirstName(),userReq.getUserName());
		user =  userDao.save(user);
		if (user == null) {
			logger.info("User not created");
			return null;
		}
		logger.info("User created with User ID" + user.getId());
		return user;
	}
		
	
	public Loan saveLoan(LoanRequest loanReq,String userName, HttpServletRequest request, User user) {
    	Date date = new Date();
		logger.info("Enter the loan Request method with Amount " + loanReq.getAmount() + " username " + userName + " Time"
				+ date.getTime());
		try {
			logger.info("check if the amount and Time not in risk margin");
			if (checkAmount(loanReq.getAmount()) && CheckTime(date)) {
					logger.info("User already exist check the previous loan counts for the same day");
					List<Loan> loans = loanDao.findByUserIdAndLoanDateAndUserIp(user.getId(),
							new java.sql.Date(date.getTime()), request.getRemoteAddr());
					if (checkLoanCount(loans.size())) {
						logger.info("Create a new Loan for the user");
						Loan loan = new Loan(date, loanReq.getAmount(), request.getRemoteAddr(), user);
						loanDao.save(loan);
						logger.info(	"New Loan Created for user " + userName + " with loan Refernce " + loan.getId());
						return loan;
					}
					logger.info("Request Rejected as Number of loans exceed maxuim number");
					return null;
				}
		} catch (Exception ex) {
			logger.error("Exception in creation loan", ex);
		}
		logger.info("Request Rejected as Risk high due to amount or Time margin");
		return null;
    }
 
    private  final Logger logger = LoggerFactory.getLogger(LoanService.class);

	public  int MAX_ALLOWED_AMOUNT;

	@Value("${max.allowed.value}")
	public void setMAximALlowedValue(int db) {
		MAX_ALLOWED_AMOUNT = db;
	}
	
	public  int MAX_ALLOWED_LOANS;

	@Value("${max.allowed.loans}")
	public void setMaxallowedLoans(int db) {
		MAX_ALLOWED_LOANS = db;
	}

	public  String RISK_START_HOURS;

	@Value("${risk.start.hours}")
	public void setRiskStartHour(String db) {
		RISK_START_HOURS = db;
	}

	public  String RISK_END_HOURS;

	@Value("${risk.end.hours}")
	public void setRiskEndHour(String db) {
		RISK_END_HOURS = db;
	}
	
	public  String TIME_FORMAT;

	@Value("${time.format}")
	public void setTimeFormat(String timeFormat) {
		TIME_FORMAT = timeFormat;
	}

	public boolean CheckTime(Date requestDate) {
		try {
			int riskStart = Integer.parseInt(RISK_START_HOURS.split(":")[0]);
			int riskEnd = Integer.parseInt(RISK_END_HOURS.split(":")[0]);

			logger.info("Start check time for the new loan");
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(requestDate);
			calendar1.set(Calendar.SECOND, 00);
			calendar1.set(Calendar.HOUR, riskStart);
			calendar1.set(Calendar.MINUTE, 00);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(requestDate);
			calendar2.set(Calendar.SECOND, 00);
			calendar2.set(Calendar.HOUR, riskEnd);
			calendar2.set(Calendar.MINUTE, 00);
			
			if(TIME_FORMAT.equalsIgnoreCase("AM")){
				calendar2.set(Calendar.AM_PM, Calendar.AM);
				calendar1.set(Calendar.AM_PM, Calendar.AM);
			}else{
				calendar1.set(Calendar.AM_PM, Calendar.PM);
				calendar2.set(Calendar.AM_PM, Calendar.PM);
			}

			if (requestDate.after(calendar1.getTime()) && requestDate.before(calendar2.getTime())) {
				logger.info("Loan requested on the Risk time margin");
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception during Time check", e);
			return false;
		}
		logger.info("Loan requested");
		return true;
	}

	public  boolean checkAmount(int amount) {
		logger.info("Start check the amount margin");
		if (MAX_ALLOWED_AMOUNT < amount) {
			return false;
		}
		return true;
	}

	public  boolean checkLoanCount(int loanCount) {
		logger.info("Start check the count for the loan requested before by the user");
		if (MAX_ALLOWED_LOANS <= loanCount) {
			return false;
		}
		return true;
	}


}
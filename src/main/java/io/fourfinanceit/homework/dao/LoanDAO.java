package io.fourfinanceit.homework.dao;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import io.fourfinanceit.homework.entity.Loan;

@Transactional
public interface LoanDAO extends CrudRepository<Loan, Integer> {
	List<Loan> findByUserId(int userId);
	List<Loan> findByUserIdAndLoanDateAndUserIp(int userId, Date loanDate, String userIp);

}
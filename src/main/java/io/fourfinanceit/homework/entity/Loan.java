package io.fourfinanceit.homework.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author
 *
 */
@Entity
@Table(name = "LOAN")
public class Loan {
	public Loan() { }

	  public Loan(int id) { 
	    this.id = id;
	  }
	  
	  public Loan(Date loanDate, int amount,String userIp,User user) {
		    this.loanDate = loanDate;
		    this.amount = amount;
		    this.userIp = userIp;
		    this.user = user;
		  }
	  
	  
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "loan_Date", columnDefinition = "DATE")
	@Type(type="date")
	private Date loanDate;

	@Column(name = "amount")
	private int amount;
	
	@Column(name = "user_ip")
	private String userIp;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the loanDate
	 */
	public Date getLoanDate() {
		return loanDate;
	}

	/**
	 * @param loanDate
	 *            the loanDate to set
	 */
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "id=" + id;
	}

	/**
	 * @return the userIp
	 */
	public String getUserIp() {
		return userIp;
	}

	/**
	 * @param userIp the userIp to set
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	

}
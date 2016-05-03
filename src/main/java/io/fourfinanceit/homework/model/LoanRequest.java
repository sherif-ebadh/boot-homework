package io.fourfinanceit.homework.model;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author
 *
 */
public class LoanRequest {
	public LoanRequest() { }


	private String firstName;
	
	private int amount;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	

}
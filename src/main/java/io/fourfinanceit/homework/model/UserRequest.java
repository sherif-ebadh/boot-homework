package io.fourfinanceit.homework.model;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author
 *
 */
public class UserRequest {
	private String firstName;
	
	private String userName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	

}
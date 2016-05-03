package io.fourfinanceit.homework.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author
 *
 */
@Entity
@Table(name = "USER")
public class User {
	public User() {
	}

	public User(int id) {
		this.id = id;
	}

	public User(String firstName, String userName) {
		this.firstName = firstName;
		this.userName = userName;

	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull(message = "Please Enter your Name")
	@Column(name = "first_name")
	private String firstName;

	@NotNull(message = "Please Enter your Name")
	@Column(name = "user_name")
	private String userName;

	@OneToMany(mappedBy = "user")
	private Set<Loan> loan;

	/**
	 * @return the loan
	 */
	public Set<Loan> getLoan() {
		return loan;
	}

	/**
	 * @param loan
	 *            the loan to set
	 */
	public void setLoan(Set<Loan> loan) {
		this.loan = loan;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "id=" + id + ", first name=" + firstName + ", user Name " + userName;
	}

}
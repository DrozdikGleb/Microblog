package com.whoslast.entities;


import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String login;

	private String password;

	private String email;

	private String firstName;

	private String lastName;

	private Integer iconId;

	public User(){}

	public User(String login, String password, String email, String firstName, String lastName, Integer iconId) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.iconId = iconId;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getIconId() {
		return iconId;
	}

	public void setIconId(Integer iconId) {
		this.iconId = iconId;
	}
}


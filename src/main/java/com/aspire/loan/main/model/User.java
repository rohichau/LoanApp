package com.aspire.loan.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;

	public User(Long id, String name, String email, String pass) {
		this.name = name;
		this.id = id;
		this.email = email;
		this.password = pass;
	}

	// Add any additional fields and methods as needed
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}
}

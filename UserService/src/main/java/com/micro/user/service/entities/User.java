package com.micro.user.service.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_user")
public class User {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	private String userId;

	@Column(name = "name", length = 20, nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "about")
	private String about;

	@Transient
	private List<Rating> ratings = new ArrayList<>();

	// Default constructor
	public User() {
	}

	// Parameterized constructor
	public User(String userId, String name, String email, String about) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.about = about;
	}

	// Getters and Setters
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
}

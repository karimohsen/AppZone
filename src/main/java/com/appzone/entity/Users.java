package com.appzone.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Users {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	int id;

	String email;

	@JsonIgnore
	String password;

	String name;

	@JsonIgnore
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	Roles role;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	List<Comments> commentsList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public List<Comments> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comments> commentsList) {
		this.commentsList = commentsList;
	}
}

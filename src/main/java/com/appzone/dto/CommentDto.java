package com.appzone.dto;

public class CommentDto {

	String comment;
	
	int userId;

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

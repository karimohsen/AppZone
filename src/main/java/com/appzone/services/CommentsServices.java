package com.appzone.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appzone.dto.CommentDto;
import com.appzone.entity.Comments;
import com.appzone.entity.Users;
import com.appzone.repository.CommentsRepository;
import com.appzone.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CommentsServices {

	@Autowired
	CommentsRepository commentsRepository;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/addcomment", consumes = "application/Json")
	public String addComment(@RequestBody String commentJson,
			HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CommentDto commentDto = mapper.readValue(commentJson, CommentDto.class);
			Comments comment = new Comments();
			comment.setComment(commentDto.getComment());
			comment.setConfirmed(false);
			Users user = userRepository.findOne(commentDto.getUserId());
			comment.setUser(user);
			commentsRepository.save(comment);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/confirmcomments", produces = "application/Json")
	public String confirmComment(@RequestBody String commentId,
			HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();
		int id = Integer.parseInt(commentId);
		Comments comment = commentsRepository.findOne(id);
		comment.setConfirmed(true);
		commentsRepository.cofirmComment(id);
		response.setHeader("Status", "200");
		try {
			OutputStream writer = response.getOutputStream();
			mapper.writeValue(writer, "comment confirmed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/showallconfirmedcomments", produces = "application/Json")
	public String getAllConfirmedComments(HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();

		List<Comments> CommentsList = commentsRepository.findByConfirmed(true);

		response.setHeader("Status", "200");
		try {
			OutputStream writer = response.getOutputStream();
			mapper.writeValue(writer, CommentsList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getallnonconfirmedcomments", produces = "application/Json")
	public String getAllNonconfirmedComments(HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();

		List<Comments> CommentsList = commentsRepository.findByConfirmed(false);

		response.setHeader("Status", "200");
		try {
			OutputStream writer = response.getOutputStream();
			mapper.writeValue(writer, CommentsList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}

}

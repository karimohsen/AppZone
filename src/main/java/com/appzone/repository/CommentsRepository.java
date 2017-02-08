package com.appzone.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appzone.entity.Comments;



@Transactional
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

	@Modifying
	@Query("update Comments c set c.confirmed = true where c.id= :id")
	public void cofirmComment(@Param("id") int id);
	public List<Comments> findByConfirmed(boolean confirmed);
}

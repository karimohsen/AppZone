package com.appzone.repository;

import javax.transaction.Transactional;

import com.appzone.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;



@Transactional
public interface UserRepository extends JpaRepository<Users, Integer>{

	public Users findByEmail(String email);
}

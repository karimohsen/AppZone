package com.appzone.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appzone.entity.Roles;

@Transactional
public interface RolesRepository extends JpaRepository<Roles, Integer>{

}

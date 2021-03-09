package com.guolong.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guolong.po.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(String username,String password);
}

package com.authentication.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.login.entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

	public User findByEmail(String username);

}

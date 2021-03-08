package com.clarity.assignment.chat.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clarity.assignment.chat.application.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
}

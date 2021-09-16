package com.udemy.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUserIdAndUserPwd(String userId, String pwd);

}

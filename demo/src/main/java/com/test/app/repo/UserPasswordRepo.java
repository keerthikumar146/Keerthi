package com.test.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.app.domain.UserPassword;

public interface UserPasswordRepo extends JpaRepository<UserPassword, Integer> {

}

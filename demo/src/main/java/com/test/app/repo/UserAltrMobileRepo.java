package com.test.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.app.domain.UserAltrMobile;

@Repository
public interface UserAltrMobileRepo extends JpaRepository<UserAltrMobile, Integer> {

}

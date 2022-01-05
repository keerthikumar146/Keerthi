package com.test.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bean.UserDetailsBean;
import com.test.app.domain.UserDetails;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer>{

	List<UserDetailsBean> save(UserDetailsBean userbean);

	List<UserDetailsBean> save(List<UserDetailsBean> userdetailsbean);

}

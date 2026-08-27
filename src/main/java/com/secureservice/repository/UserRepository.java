package com.secureservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secureservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

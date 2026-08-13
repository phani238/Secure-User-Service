package com.example.secureservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.secureservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

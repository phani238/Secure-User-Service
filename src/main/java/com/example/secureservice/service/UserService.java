package com.example.secureservice.service;

import java.util.List;

import com.example.secureservice.dto.UserRequest;
import com.example.secureservice.dto.UserResponse;

public interface UserService {
	UserResponse createUser(UserRequest user);

	List<UserResponse> getAllUsers();

	UserResponse getUserById(Long id);

	UserResponse updateUser(Long id, UserRequest user);

	void deleteUser(Long id);
}

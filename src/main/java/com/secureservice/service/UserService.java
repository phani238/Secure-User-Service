package com.secureservice.service;

import java.util.List;

import com.secureservice.dto.UserRequest;
import com.secureservice.dto.UserResponse;

public interface UserService {
	UserResponse createUser(UserRequest user);

	List<UserResponse> getAllUsers();

	UserResponse getUserById(Long id);

	UserResponse updateUser(Long id, UserRequest user);

	void deleteUser(Long id);
}

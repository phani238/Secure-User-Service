package com.example.secureservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.secureservice.entity.User;
import com.example.secureservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public User updateUser(Long id, User user) {

		User existingUser = getUserById(id);

		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());

		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}

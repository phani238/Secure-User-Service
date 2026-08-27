package com.secureservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secureservice.dto.UserRequest;
import com.secureservice.dto.UserResponse;
import com.secureservice.entity.User;
import com.secureservice.event.UserEvent;
import com.secureservice.exception.UserNotFoundException;
import com.secureservice.repository.UserRepository;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final MeterRegistry meterRegistry;
	private final KafkaProducerService kafkaProducerService;

	public UserServiceImpl(UserRepository userRepository, MeterRegistry meterRegistry,
			KafkaProducerService kafkaProducerService) {
		this.userRepository = userRepository;
		this.meterRegistry = meterRegistry;
		this.kafkaProducerService = kafkaProducerService;
	}

	@Override
	public UserResponse createUser(UserRequest request) {
		log.info("Creating new user with email: {}", request.getEmail());
		log.debug("User request received for email: {}", request.getEmail());
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		User savedUser = userRepository.save(user);
		meterRegistry.counter("users.created").increment();

		UserEvent event = UserEvent.builder().userId(savedUser.getId()).name(savedUser.getName())
				.email(savedUser.getEmail()).eventType("USER_CREATED").build();
		kafkaProducerService.publishUserCreatedEvent(event);

		return toResponse(savedUser);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			log.warn("User not found with id: {}", id);
			return new UserNotFoundException("User not found with id:" + id);
		});
		return toResponse(user);
	}

	@Override
	public UserResponse updateUser(Long id, UserRequest request) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id:" + id));
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		User updatedUser = userRepository.save(user);
		return toResponse(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
		userRepository.delete(user);
	}

	private UserResponse toResponse(User user) {
		return new UserResponse(user.getId(), user.getName(), user.getEmail());
	}
}

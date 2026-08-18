package com.example.secureservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.secureservice.dto.UserRequest;
import com.example.secureservice.dto.UserResponse;
import com.example.secureservice.entity.User;
import com.example.secureservice.exception.UserNotFoundException;
import com.example.secureservice.repository.UserRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private MeterRegistry meterRegistry;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getUserById_shouldReturnUser_whenUserExists() {

		// Arrange
		User user = new User();
		user.setId(1L);
		user.setName("John");
		user.setEmail("john@test.com");

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		// Act
		UserResponse response = userService.getUserById(1L);

		// Assert
		assertEquals(1L, response.getId());
		assertEquals("John", response.getName());
		assertEquals("john@test.com", response.getEmail());

		verify(userRepository).findById(1L);
	}

	@Test
	void getUserById_shouldThrowException_whenUserDoesNotExist() {

		// Arrange
		Mockito.when(userRepository.findById(99L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));

		verify(userRepository).findById(99L);
	}

	@Test
	void createUser_shouldCreateUserAndIncrementMetric() {

		// Arrange
		UserRequest request = new UserRequest();
		request.setName("John");
		request.setEmail("john@test.com");

		User savedUser = new User();
		savedUser.setId(1L);
		savedUser.setName("John");
		savedUser.setEmail("john@test.com");

		Counter counter = Mockito.mock(Counter.class);

		when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

		when(meterRegistry.counter("users.created")).thenReturn(counter);

		// Act
		UserResponse response = userService.createUser(request);

		// Assert
		assertEquals(1L, response.getId());
		assertEquals("John", response.getName());
		assertEquals("john@test.com", response.getEmail());

		verify(userRepository).save(Mockito.any(User.class));
		verify(meterRegistry).counter("users.created");
		verify(counter).increment();
	}

	@Test
	void updateUser_shouldUpdateUser_whenUserExists() {

		// Arrange
		UserRequest request = new UserRequest();
		request.setName("John Updated");
		request.setEmail("john.updated@test.com");

		User existingUser = new User();
		existingUser.setId(1L);
		existingUser.setName("John");
		existingUser.setEmail("john@test.com");

		User savedUser = new User();
		savedUser.setId(1L);
		savedUser.setName("John Updated");
		savedUser.setEmail("john.updated@test.com");

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

		when(userRepository.save(existingUser)).thenReturn(savedUser);

		// Act
		UserResponse response = userService.updateUser(1L, request);

		// Assert
		assertEquals(1L, response.getId());
		assertEquals("John Updated", response.getName());
		assertEquals("john.updated@test.com", response.getEmail());

		verify(userRepository).findById(1L);
		verify(userRepository).save(existingUser);
	}

	@Test
	void updateUser_shouldThrowException_whenUserDoesNotExist() {

		// Arrange
		UserRequest request = new UserRequest();
		request.setName("John Updated");
		request.setEmail("john.updated@test.com");

		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userService.updateUser(99L, request));

		verify(userRepository).findById(99L);
		verify(userRepository, never()).save(Mockito.any(User.class));
	}

	@Test
	void getAllUsers_shouldReturnAllUsers() {

		// Arrange
		User user1 = new User();
		user1.setId(1L);
		user1.setName("John");
		user1.setEmail("john@test.com");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("Mike");
		user2.setEmail("mike@test.com");

		when(userRepository.findAll()).thenReturn(List.of(user1, user2));

		// Act
		List<UserResponse> responses = userService.getAllUsers();

		// Assert
		assertEquals(2, responses.size());

		assertEquals(1L, responses.get(0).getId());
		assertEquals("John", responses.get(0).getName());

		assertEquals(2L, responses.get(1).getId());
		assertEquals("Mike", responses.get(1).getName());

		verify(userRepository).findAll();
	}

	@Test
	void deleteUser_shouldDeleteUser_whenUserExists() {

		// Arrange
		User user = new User();
		user.setId(1L);
		user.setName("John");
		user.setEmail("john@test.com");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		// Act
		userService.deleteUser(1L);

		// Assert
		verify(userRepository).findById(1L);
		verify(userRepository).delete(user);
	}

	@Test
	void deleteUser_shouldThrowException_whenUserDoesNotExist() {

		// Arrange
		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L));

		verify(userRepository).findById(99L);
		verify(userRepository, never()).delete(Mockito.any(User.class));
	}
}
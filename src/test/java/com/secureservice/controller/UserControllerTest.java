package com.secureservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.secureservice.dto.UserRequest;
import com.secureservice.dto.UserResponse;
import com.secureservice.exception.UserNotFoundException;
import com.secureservice.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	void createUser_shouldReturn201() throws Exception {

		// Arrange
		UserResponse response = new UserResponse(1L, "John", "john@test.com");
		when(userService.createUser(any(UserRequest.class))).thenReturn(response);
		String requestJson = """
				{
				    "name": "John",
				    "email": "john@test.com"
				}
				""";

		// Act & Assert
		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("John")).andExpect(jsonPath("$.email").value("john@test.com"));
	}

	@Test
	void getAllUsers_shouldReturn200() throws Exception {

		// Arrange
		UserResponse user1 = new UserResponse(1L, "John", "john@test.com");
		UserResponse user2 = new UserResponse(2L, "Mike", "mike@test.com");
		when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

		// Act & Assert
		mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("John")).andExpect(jsonPath("$[0].email").value("john@test.com"))
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].name").value("Mike"))
				.andExpect(jsonPath("$[1].email").value("mike@test.com"));
	}

	@Test
	void getUserById_shouldReturn200_whenUserExists() throws Exception {

		// Arrange
		UserResponse response = new UserResponse(1L, "John", "john@test.com");
		when(userService.getUserById(1L)).thenReturn(response);

		// Act & Assert
		mockMvc.perform(get("/api/users/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("John")).andExpect(jsonPath("$.email").value("john@test.com"));
	}

	@Test
	void getUserById_shouldReturn404_whenUserDoesNotExist() throws Exception {

		// Arrange
		when(userService.getUserById(99L)).thenThrow(new UserNotFoundException("User not found with id: 99"));

		// Act & Assert
		mockMvc.perform(get("/api/users/99")).andExpect(status().isNotFound());
	}

	@Test
	void createUser_shouldReturn400_whenNameIsBlank() throws Exception {

		// Arrange
		String requestJson = """
				{
				    "name": "",
				    "email": "john@test.com"
				}
				""";

		// Act & Assert
		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createUser_shouldReturn400_whenEmailIsInvalid() throws Exception {

		// Arrange
		String requestJson = """
				{
				    "name": "John",
				    "email": "invalid-email"
				}
				""";

		// Act & Assert
		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isBadRequest());

		verify(userService, never()).createUser(any(UserRequest.class));
	}

	@Test
	void updateUser_shouldReturn200_whenUserExists() throws Exception {

		// Arrange
		UserResponse response = new UserResponse(1L, "John Updated", "john.updated@test.com");
		when(userService.updateUser(org.mockito.ArgumentMatchers.eq(1L), any(UserRequest.class))).thenReturn(response);

		String requestJson = """
				{
				    "name": "John Updated",
				    "email": "john.updated@test.com"
				}
				""";

		// Act & Assert
		mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("John Updated"))
				.andExpect(jsonPath("$.email").value("john.updated@test.com"));
	}

	@Test
	void updateUser_shouldReturn400_whenNameIsBlank() throws Exception {

		// Arrange
		String requestJson = """
				{
				    "name": "",
				    "email": "john.updated@test.com"
				}
				""";

		// Act & Assert
		mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isBadRequest());

		verify(userService, never()).updateUser(eq(1L), any(UserRequest.class));
	}

	@Test
	void updateUser_shouldReturn400_whenEmailIsInvalid() throws Exception {

		// Arrange
		String requestJson = """
				{
				    "name": "John Updated",
				    "email": "invalid-email"
				}
				""";

		// Act & Assert
		mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isBadRequest());

		verify(userService, never()).updateUser(eq(1L), any(UserRequest.class));
	}

	@Test
	void deleteUser_shouldReturn204_whenUserExists() throws Exception {

		// Act & Assert
		mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());

		verify(userService).deleteUser(1L);
	}

	@Test
	void deleteUser_shouldReturn404_whenUserDoesNotExist() throws Exception {

		// Arrange
		doThrow(new UserNotFoundException("User not found with id: 99")).when(userService).deleteUser(99L);

		// Act & Assert
		mockMvc.perform(delete("/api/users/99")).andExpect(status().isNotFound());

		verify(userService).deleteUser(99L);
	}
}

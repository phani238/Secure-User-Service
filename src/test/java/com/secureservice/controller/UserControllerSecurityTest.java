package com.secureservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.secureservice.config.SecurityConfig;
import com.secureservice.dto.UserRequest;
import com.secureservice.dto.UserResponse;
import com.secureservice.service.UserService;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerSecurityTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	void getUsers_shouldReturn401_whenUnauthenticated() throws Exception {
		mockMvc.perform(get("/api/users")).andExpect(status().isUnauthorized());
	}

	@Test
	void getUsers_shouldReturn200_whenUserIsAuthenticated() throws Exception {
		mockMvc.perform(get("/api/users").with(user("john").roles("USER"))).andExpect(status().isOk());
	}

	@Test
	void createUser_shouldReturn403_whenUserRole() throws Exception {

		String requestJson = """
				{
				    "name": "John",
				    "email": "john@test.com"
				}
				""";
		mockMvc.perform(post("/api/users").with(user("john").roles("USER")).contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andExpect(status().isForbidden());

		verify(userService, never()).createUser(any(UserRequest.class));
	}

	@Test
	void createUser_shouldReturn201_whenAdminRole() throws Exception {

		UserResponse response = new UserResponse(1L, "John", "john@test.com");

		when(userService.createUser(any(UserRequest.class))).thenReturn(response);

		String requestJson = """
				{
				    "name": "John",
				    "email": "john@test.com"
				}
				""";

		mockMvc.perform(post("/api/users").with(user("admin").roles("ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("John"))
				.andExpect(jsonPath("$.email").value("john@test.com"));

		verify(userService).createUser(any(UserRequest.class));
	}

	@Test
	void updateUser_shouldReturn403_whenUserRole() throws Exception {

		String requestJson = """
				{
				    "name": "John Updated",
				    "email": "john.updated@test.com"
				}
				""";

		mockMvc.perform(put("/api/users/1").with(user("john").roles("USER")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isForbidden());

		verify(userService, never()).updateUser(eq(1L), any(UserRequest.class));
	}

	@Test
	void updateUser_shouldReturn200_whenAdminRole() throws Exception {

		UserResponse response = new UserResponse(1L, "John Updated", "john.updated@test.com");

		when(userService.updateUser(eq(1L), any(UserRequest.class))).thenReturn(response);

		String requestJson = """
				{
				    "name": "John Updated",
				    "email": "john.updated@test.com"
				}
				""";

		mockMvc.perform(put("/api/users/1").with(user("admin").roles("ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("John Updated"))
				.andExpect(jsonPath("$.email").value("john.updated@test.com"));

		verify(userService).updateUser(eq(1L), any(UserRequest.class));
	}

	@Test
	void deleteUser_shouldReturn403_whenUserRole() throws Exception {

		mockMvc.perform(delete("/api/users/1").with(user("john").roles("USER")).with(csrf()))
				.andExpect(status().isForbidden());

		verify(userService, never()).deleteUser(1L);
	}

	@Test
	void deleteUser_shouldReturn204_whenAdminRole() throws Exception {

		mockMvc.perform(delete("/api/users/1").with(user("admin").roles("ADMIN")).with(csrf()))
				.andExpect(status().isNoContent());

		verify(userService).deleteUser(1L);
	}

}
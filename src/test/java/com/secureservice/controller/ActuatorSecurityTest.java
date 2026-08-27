package com.secureservice.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorSecurityTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void health_shouldReturn200_whenUnauthenticated() throws Exception {
		mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
	}

	@Test
	void metrics_shouldReturn403_whenUserRole() throws Exception {

		mockMvc.perform(get("/actuator/metrics/http.server.requests").with(user("john").roles("USER")))
				.andExpect(status().isForbidden());
	}

	@Test
	void metrics_shouldReturn200_whenAdminRole() throws Exception {
		mockMvc.perform(get("/actuator/metrics").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk());
	}
}
package com.example.hello;

import com.example.hello.controller.HelloController;
import com.example.hello.dto.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloApplicationTests {

	@Autowired
	private HelloController controller;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void testLogin() {
		String result = controller.login("test@example.com", "1234");
		assertThat(result).isEqualTo("OK");
	}

	@Test
	void testLoginFailed() {
		String result = controller.login("test1@example.com", "123");
		assertThat(result).isEqualTo("FAILED");
	}

	@Test
	void testSignIn() {
		RequestLogin body = new RequestLogin();
		body.setId("test@example.com");
		body.setPassword("1234");

		String result = controller.signin(body);
		assertThat(result).isEqualTo("OK");
	}

	@Test
	void testLoginMockMvc() throws Exception {
		mockMvc.perform(get("/login")
						.param("id", "test@example.com")
						.param("password", "1234"))
				.andExpect(status().isOk())
				.andExpect(content().string("OK"));
	}

	@Test
	void testSignInMockMvc() throws Exception {
		RequestLogin body = new RequestLogin();
		body.setId("test@example.com");
		body.setPassword("1234");

		mockMvc.perform(post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body)))
				.andExpect(status().isOk())
				.andExpect(content().string("OK"));
	}

	@Test
	void testGetUserSuccess() throws Exception {
		mockMvc.perform(get("/user/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("""
                {
                    "id": "1",
                    "name": "홍길동",
                    "email": "test@example.com"
                }
            """));
	}
	@Test
	void testGetUserNotFound() throws Exception {
		mockMvc.perform(get("/user/2"))
				.andExpect(status().isNotFound());
	}
	@Test
	void testGetUserBadRequest() throws Exception {
		mockMvc.perform(get("/user/0"))
				.andExpect(status().isBadRequest());
	}
}
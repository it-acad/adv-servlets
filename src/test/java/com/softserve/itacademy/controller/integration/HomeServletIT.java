package com.softserve.itacademy.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class HomeServletIT extends BaseIT {

    @Test
    @Order(1)
    @DisplayName("GET / should display the home page")
    void testRootPath() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Welcome to the To-Do List Application"),
                    "Expected welcome message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testRootPath: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("GET /home should display the home page")
    void testHomePath() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");
        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/home")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Welcome to the To-Do List Application"),
                    "Expected welcome message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testHomePath: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

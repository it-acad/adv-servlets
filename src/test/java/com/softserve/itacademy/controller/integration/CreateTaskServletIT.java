package com.softserve.itacademy.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class CreateTaskServletIT extends BaseIT {

    @Test
    @Order(1)
    @DisplayName("GET /create-task should display the create task form")
    void testGetRequest() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/create-task")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testGetRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("POST /create-task should create task and redirect")
    void testValidPostRequest() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        try {
            WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.POST)
                    .uri("/create-task")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("title", "Unique Task " + System.currentTimeMillis())
                            .with("priority", "HIGH"))
                    .exchange()
                    .expectStatus().isFound()
                    .expectBody().returnResult();
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testValidPostRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("POST /create-task should display error for duplicate title")
    void testInvalidPostRequest() {

        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.POST)
                    .uri("/create-task")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("title", "Task #1")
                            .with("priority", "LOW"))
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Task with a given name already exists!"), "Expected duplicate title error message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testInvalidPostRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

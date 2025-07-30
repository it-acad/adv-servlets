package com.softserve.itacademy.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ReadTaskServletIT extends BaseIT {

    @Test
    @Order(1)
    @DisplayName("GET /read-task with valid ID should return 404 status code")
    void testValidGetRequest() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        int taskId = testTasks.get(0).getId();

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/read-task?id=" + taskId)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Task with ID '" + taskId + "' not found in To-Do List!"),
                    "Expected error message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testValidGetRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("GET /read-task with invalid ID should return 200 OK with error message")
    void testInvalidGetRequest() {

        assumeTrue(isServerAvailable(), "Skipping test because server is not available");
        int nonExistentId = testTasks.size() + 100;

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/read-task?id=" + nonExistentId)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Task with ID '" + nonExistentId + "' not found in To-Do List!") ||
                            strBody.contains("Error Occurred"),
                    "Expected error message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testInvalidGetRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

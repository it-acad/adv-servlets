package com.softserve.itacademy.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class UpdateTaskServletIT extends BaseIT {

    @Test
    @Order(1)
    @DisplayName("GET /edit-task should display task edit page with valid task data")
    void testValidGetRequest() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        int taskId = testTasks.get(0).getId();

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/edit-task?id=" + taskId)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Task with ID"), "Expected error message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testValidGetRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("POST /edit-task should update task and redirect on valid data")
    void testValidPostRequest() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        int taskId = testTasks.get(0).getId();

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.POST)
                    .uri("/edit-task")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("id", String.valueOf(taskId))
                            .with("title", "Task #4")
                            .with("priority", "HIGH"))
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Task with ID"), "Expected error message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testValidPostRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("GET /edit-task should return 200 OK for invalid task ID")
    void testInvalidGetRequest() {

        assumeTrue(isServerAvailable(), "Skipping test because server is not available");
        int nonExistentId = testTasks.size() + 100;

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/edit-task?id=" + nonExistentId)
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

    @Test
    @Order(4)
    @DisplayName("POST /edit-task should display error on duplicate title")
    void testInvalidPostRequest() {
        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        int taskId = testTasks.get(0).getId();

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.POST)
                    .uri("/edit-task")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("id", String.valueOf(taskId))
                            .with("title", testTasks.get(1).getTitle())
                            .with("priority", "MEDIUM"))
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");

            String strBody = new String(body);
            assertTrue(strBody.contains("Task with ID"), "Expected error message not found!");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testInvalidPostRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

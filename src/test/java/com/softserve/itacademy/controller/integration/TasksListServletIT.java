package com.softserve.itacademy.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TasksListServletIT extends BaseIT {

    @Test
    @Order(1)
    @DisplayName("GET /tasks-list should return task list page")
    void testValidGetRequest() {

        assumeTrue(isServerAvailable(), "Skipping test because server is not available");

        try {
            byte[] body = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + WEB_PORT)
                    .build()
                    .method(HttpMethod.GET)
                    .uri("/tasks-list")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType("text/html;charset=UTF-8")
                    .expectBody().returnResult().getResponseBody();

            assertNotNull(body, "Response body is null");
            assertTrue(body.length > 0, "Response body is empty");
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Error in testValidGetRequest: " + e.getMessage());
            fail("Test failed with exception: " + e.getMessage());
        }
    }
}

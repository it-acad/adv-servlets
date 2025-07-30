package com.softserve.itacademy.controller.integration;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for running all integration tests in JUnit 5
 * Tests are now independent of execution order and specific IDs
 */
@Suite
@SelectClasses({
        HomeServletIT.class,
        CreateTaskServletIT.class,
        ReadTaskServletIT.class,
        UpdateTaskServletIT.class,
        DeleteTaskServletIT.class,
        TasksListServletIT.class
})
public class IntegrationTestSuite {
}

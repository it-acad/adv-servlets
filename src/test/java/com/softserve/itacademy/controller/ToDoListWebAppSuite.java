package com.softserve.itacademy.controller;

import com.softserve.itacademy.controller.integration.IntegrationTestSuite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Main test suite that includes both unit tests and integration tests
 */
@Suite
@SelectClasses({
        UnitTestSuite.class,
        IntegrationTestSuite.class
})
public class ToDoListWebAppSuite {  }

package com.softserve.itacademy.controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for running all unit tests
 */
@Suite
@SelectClasses({
        HomeServletTest.class,
        CreateTaskServletTest.class,
        ReadTaskServletTest.class,
        UpdateTaskServletTest.class,
        DeleteTaskServletTest.class,
        TasksListServletTest.class
})
public class UnitTestSuite {  }

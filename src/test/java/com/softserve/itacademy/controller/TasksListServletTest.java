package com.softserve.itacademy.controller;

import com.softserve.itacademy.repository.TaskRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Unit tests for TasksListServlet
 */
class TasksListServletTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private final TasksListServlet tasksListServlet = new TasksListServlet();

    @BeforeEach
    void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Servlet should retrieve tasks list from repository")
    void testCorrectTasksRead() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(taskRepository.all()).thenReturn(Collections.emptyList());

        tasksListServlet.doGet(request, response);

        verify(taskRepository, times(1)).all();
        verify(request).getRequestDispatcher("/WEB-INF/pages/tasks-list.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}

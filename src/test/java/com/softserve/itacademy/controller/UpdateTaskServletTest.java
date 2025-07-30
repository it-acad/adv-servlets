package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UpdateTaskServlet
 */
class UpdateTaskServletTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private final UpdateTaskServlet updateTaskServlet = new UpdateTaskServlet();

    @BeforeEach
    void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Servlet should call update method in repository")
    void testCorrectTaskUpdate() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("Task #3");
        when(request.getParameter("priority")).thenReturn("MEDIUM");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        Task existingTask = new Task("Task #1", Priority.LOW);
        when(taskRepository.read(1)).thenReturn(existingTask);

        when(taskRepository.update(any(Task.class))).thenReturn(true);

        updateTaskServlet.doPost(request, response);

        verify(taskRepository, times(1)).update(any(Task.class));
    }
}

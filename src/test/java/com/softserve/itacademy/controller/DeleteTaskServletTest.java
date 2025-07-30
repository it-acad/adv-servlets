package com.softserve.itacademy.controller;

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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DeleteTaskServlet
 */
class DeleteTaskServletTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private final DeleteTaskServlet deleteTaskServlet = new DeleteTaskServlet();

    @BeforeEach
    void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Repository should correctly delete task with valid ID")
    void testCorrectTaskDelete() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("3");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        Task existingTask = new Task("Task #3", com.softserve.itacademy.model.Priority.LOW);
        when(taskRepository.read(3)).thenReturn(existingTask);

        when(taskRepository.delete(anyInt())).thenReturn(true);

        deleteTaskServlet.doGet(request, response);

        verify(taskRepository, times(1)).delete(anyInt());
    }
}

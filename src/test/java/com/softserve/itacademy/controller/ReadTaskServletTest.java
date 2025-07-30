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
 * Unit tests for ReadTaskServlet
 */
class ReadTaskServletTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private final ReadTaskServlet readTaskServlet = new ReadTaskServlet();

    @BeforeEach
    void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Repository should correctly read task with valid ID")
    void testCorrectTaskRead() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("3");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(taskRepository.read(anyInt())).thenReturn(new Task());

        readTaskServlet.doGet(request, response);

        verify(taskRepository, times(1)).read(anyInt());
    }
}

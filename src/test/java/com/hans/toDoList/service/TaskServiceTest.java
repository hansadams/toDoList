package com.hans.toDoList.service;

import com.hans.toDoList.exception.ResourceNotFoundException;
import com.hans.toDoList.model.Task;
import com.hans.toDoList.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;
    private Task updatedTask;

    @BeforeEach
    void init(){
        task1 = new Task("Task 1", true);
        task2 = new Task("Task 2", false);
        updatedTask = new Task("Updated Task", false);
        task1.setId(1L);
        task1.setId(2L);
    }

    @Test
    void createNewTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task newProduct = taskService.createNewTask(task1);

        assertNotNull(newProduct);
        assertThat(newProduct.getTask()).isEqualTo("Task 1");
        assertThat(newProduct.isCompleted()).isEqualTo(true);
    }

    @Test
    void getAllTask() {
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(taskRepository.findAll()).thenReturn(list);

        List<Task> results = taskService.getAllTask();

        assertEquals(2, results.size());
        assertNotNull(results);
    }

    @Test
    void findTaskById() throws ResourceNotFoundException {

        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(task1));
        Task currentTask = taskService.findTaskById(task1.getId());

        assertNotNull(currentTask);

    }

    @Test
    void findAllCompletedTask() {
        List<Task> list = new ArrayList<>();
        list.add(task1);

        when(taskRepository.findByCompletedTrue()).thenReturn(list);

        List<Task> results = taskService.findAllCompletedTask();

        assertEquals(1, results.size());
        assertNotNull(results);
    }

    @Test
    void findAllInCompleteTask() {
        List<Task> list = new ArrayList<>();
        list.add(task2);

        when(taskRepository.findByCompletedFalse()).thenReturn(list);

        List<Task> results = taskService.findAllInCompleteTask();

        assertEquals(1, results.size());
        assertNotNull(results);
    }

    @Test
    void deleteTask() {
        List<Task> list = new ArrayList<>();
        list.add(task1);

        when(taskRepository.findAll()).thenReturn(list);

        taskService.deleteTask(task2);
        List<Task> results = taskService.getAllTask();

        assertEquals(1, results.size());
        assertNotNull(results);
    }

    @Test
    void updateTask() {

        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Optional<Task> currentProduct = taskService.updateTask(task1, updatedTask);

        assertNotNull(currentProduct);
        assertEquals("Updated Task", task1.getTask());
        assertFalse(task1.isCompleted());
    }
}
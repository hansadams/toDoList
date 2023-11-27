package com.hans.toDoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hans.toDoList.model.Task;
import com.hans.toDoList.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task task1;
    private Task task2;
    private Task updatedTask;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        task1 = new Task("Task 1", true);
        task2 = new Task("Task 2", false);
        updatedTask = new Task("Updated Task", true);
    }

    @Test
    void getAllTasks() throws Exception {
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(taskService.getAllTask()).thenReturn(list);

        this.mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void getAllCompletedTasks() throws Exception {
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(taskService.findAllCompletedTask()).thenReturn(list);

        this.mockMvc.perform(get("/api/v1/tasks/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));

    }

    @Test
    void getAllIncompleteTasks() throws Exception{
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(taskService.findAllInCompleteTask()).thenReturn(list);

        this.mockMvc.perform(get("/api/v1/tasks/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void createTask() throws Exception {
        when(taskService.createNewTask(any(Task.class))).thenReturn(task1);

        this.mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task", is(task1.getTask())))
                .andExpect(jsonPath("$.completed", is(task1.isCompleted())));
    }

    @Test
    void updateTask() throws Exception {

        when(taskService.updateTask(any(Task.class), any(Task.class)))
                .thenReturn(Optional.ofNullable(updatedTask));

        this.mockMvc.perform(put("/api/v1/tasks/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.task", is(updatedTask.getTask())))
                        .andExpect(jsonPath("$.completed", is(updatedTask.isCompleted())));
    }


    @Test
    void deleteTasks() throws Exception {
        when(taskService.deleteTask(any(Task.class)))
                .thenReturn(Optional.ofNullable(task1));

        this.mockMvc.perform(delete("/api/v1/tasks/{id}", "1"))
                .andExpect(status().isOk());

    }
}
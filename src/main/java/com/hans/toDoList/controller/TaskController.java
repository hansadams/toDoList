package com.hans.toDoList.controller;

import com.hans.toDoList.exception.ResourceNotFoundException;
import com.hans.toDoList.model.Task;
import com.hans.toDoList.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @GetMapping
    public ResponseEntity<Object> getAllTasks() {
        List<Task> result = taskService.getAllTask();

        if(!result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Tasks Available", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/completed")
    public ResponseEntity<Object> getAllCompletedTasks() {
        List<Task> result = taskService.findAllCompletedTask();

        if(!result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Completed Tasks Available", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/incomplete")
    public ResponseEntity<Object> getAllIncompleteTasks() {
        List<Task> result = taskService.findAllInCompleteTask();

        if(!result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Incomplete Tasks Available", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.createNewTask(task));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) throws ResourceNotFoundException {
        Optional<Task> result = taskService.updateTask(taskService.findTaskById(id), task);
        return ResponseEntity.ok(task);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTasks(@PathVariable Long id) throws ResourceNotFoundException {
        taskService.deleteTask(taskService.findTaskById(id));
        return ResponseEntity.ok("Task" +id+ "deleted successfully.");
    }
}

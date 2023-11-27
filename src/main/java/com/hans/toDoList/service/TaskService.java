package com.hans.toDoList.service;

import com.hans.toDoList.exception.ResourceNotFoundException;
import com.hans.toDoList.model.Task;
import com.hans.toDoList.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createNewTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) throws ResourceNotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        return taskRepository.findByCompletedFalse();
    }

    public Optional<Task> deleteTask(Task task) {
        taskRepository.delete(task);
        return Optional.of(task);
    }

    public Optional<Task> updateTask(Task task, Task newTask) {
        Optional<Task> result = Optional.ofNullable(task);

        try{
            Task temp = result.get();
            temp.setTask(newTask.getTask());
            temp.setCompleted(newTask.isCompleted());
            return Optional.of(taskRepository.save(temp));
        }catch(Exception e){
            return result;
        }
    }
}

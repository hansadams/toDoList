package com.hans.toDoList.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // this is the primary key which will be auto generated
    private Long id;
    @NotNull
    @Size(min = 2, message = "Task should have at least 2 characters")
    private String task;
    @NotNull
    private Boolean completed;

    public Task (){

    }

    public Task(String task, Boolean completed) {
        this.task = task;
        this.completed = completed;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

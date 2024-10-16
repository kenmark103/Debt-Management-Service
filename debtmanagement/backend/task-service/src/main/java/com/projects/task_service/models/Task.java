package com.projects.task_service.models;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    @Generated("autoincrement")
    private Long id;
    private String name;
    private TaskType type;
    private LocalDate createdDate;
    private TaskStatus status;
    private String description;
}

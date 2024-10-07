package com.example.todolist.dto;

import com.example.todolist.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Long userId;
}

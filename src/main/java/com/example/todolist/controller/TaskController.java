package com.example.todolist.controller;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.enums.Status;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.addTask(taskDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/done/{id}")
    public ResponseEntity<TaskDTO> doneTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.doneTask(id));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Status> getStatusByTaskId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getStatusByTaskId(id));
    }
}

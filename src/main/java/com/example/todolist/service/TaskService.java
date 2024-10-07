package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.enums.Status;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found {user не найдена}")
        );
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(Status.ACTIVE);

        task.setUserId(user.getId());
        task.setUser(user);



        taskRepository.save(task);
        return toTask(task);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("not found {как могу обновить не существующею задачу???}"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        if (task.getStatus().equals(Status.ACTIVE)) {
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Task status is not ACTIVE {не можете обновить задачу, так-как она уже сделанно!}");
        }
        return toTask(task);
    }

    public TaskDTO doneTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("not found"));
        task.setStatus(Status.DONE);
        taskRepository.save(task);
        return toTask(task);
    }

    public TaskDTO getById(Long id) {
        return toTask(taskRepository.findById(id).orElseThrow());
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toTask).toList();
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id).ifPresentOrElse(
                task -> {
                    if (task.getStatus().equals(Status.DONE)) {
                        taskRepository.delete(task);
                        return;
                    }
                    throw new RuntimeException("не могу удалить акцивную задачу!");
                },
                () -> {
                    throw new RuntimeException("Task not found {задания не найдена}");
                }
        );
    }

    public Status getStatusByTaskId(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("not found {не найден задача с такой Id}")

        );
        return task.getStatus();
    }

    public TaskDTO toTask(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setUserId(task.getUserId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }
}

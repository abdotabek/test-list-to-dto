package com.example.todolist.repository;

import com.example.todolist.entity.Task;
import com.example.todolist.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Status getStatusById(Long id);
}

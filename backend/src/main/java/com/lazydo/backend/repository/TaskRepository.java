package com.lazydo.backend.repository;

import com.lazydo.backend.domain.Task;
import com.lazydo.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}

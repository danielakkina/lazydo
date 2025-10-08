package com.lazydo.backend.service;

import com.lazydo.backend.domain.Task;
import com.lazydo.backend.domain.User;
import com.lazydo.backend.repository.TaskRepository;
import com.lazydo.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task createTask(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task markTaskAsCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.isCompleted()) {
            task.setCompleted(true);
            // give dopamine points
            User user = task.getUser();
            user.setDopaminePoints(user.getDopaminePoints() + task.getRewardPoints());
            userRepository.save(user);
        }
        return taskRepository.save(task);
    }
}

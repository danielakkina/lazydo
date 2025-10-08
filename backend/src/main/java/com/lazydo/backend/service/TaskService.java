package com.lazydo.backend.service;

import com.lazydo.backend.domain.Task;
import com.lazydo.backend.domain.User;
import com.lazydo.backend.repository.TaskRepository;
import com.lazydo.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            User user = task.getUser();

            LocalDate today = LocalDate.now();

            // Check if user completed tasks today
            if (user.getLastActiveDate() == null || !user.getLastActiveDate().equals(today)) {
                // User did not complete any task today → increase streak
                LocalDate yesterday = today.minusDays(1);

                if (yesterday.equals(user.getLastActiveDate())) {
                    // continued streak
                    user.setStreakCount(user.getStreakCount() + 1);
                } else {
                    // missed a day, reset streak
                    user.setStreakCount(1);
                }

                // update streak and last active date
                user.setLastActiveDate(today);
                user.setStreakLastUpdated(today);
            }

            // Reward dopamine points + streak bonus
            int totalReward = task.getRewardPoints() + (user.getStreakCount() * 2);
            user.setDopaminePoints(user.getDopaminePoints() + totalReward);

            userRepository.save(user);
        }

        return taskRepository.save(task);
    }
}

package com.lazydo.backend.api;

import com.lazydo.backend.domain.Task;
import com.lazydo.backend.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{userId}")
    public List<Task> getUserTasks(@PathVariable Long userId) {
        return taskService.getTasksByUser(userId);
    }

    @PostMapping("/{userId}")
    public Task createTask(@PathVariable Long userId, @RequestBody Task task) {
        return taskService.createTask(userId, task);
    }

    @PutMapping("/{taskId}/complete")
    public Task completeTask(@PathVariable Long taskId) {
        return taskService.markTaskAsCompleted(taskId);
    }
}

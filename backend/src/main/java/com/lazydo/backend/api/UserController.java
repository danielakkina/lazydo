package com.lazydo.backend.api;

import com.lazydo.backend.domain.User;
import com.lazydo.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getAllUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping("/{id}/progress")
    public Map<String, Object> getUserProgress(@PathVariable Long id) {
        User user = service.getUserById(id);
        Map<String, Object> progress = new HashMap<>();
        progress.put("name", user.getName());
        progress.put("streakCount", user.getStreakCount());
        progress.put("dopaminePoints", user.getDopaminePoints());
        progress.put("lastActiveDate", user.getLastActiveDate());
        return progress;
    }

}

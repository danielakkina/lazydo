package com.lazydo.backend.service;

import com.lazydo.backend.domain.User;
import com.lazydo.backend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulerService {

    private final UserRepository userRepository;

    public SchedulerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Runs every midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void resetStreaksIfMissed() {
        LocalDate today = LocalDate.now();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            LocalDate last = user.getLastActiveDate();
            if (last != null && last.isBefore(today.minusDays(1))) {
                user.setStreakCount(0);
                userRepository.save(user);
            }
        }
        System.out.println("✅ Streak reset check completed for all users.");
    }
}

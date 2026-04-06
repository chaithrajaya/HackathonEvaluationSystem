package com.example.HackathonEvaluator;

import com.example.HackathonEvaluator.entity.User;
import com.example.HackathonEvaluator.repository.UserRepository;
import com.example.HackathonEvaluator.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
        }

        if(userRepository.findByEmail("judge@example.com").isEmpty()) {
            User judge = new User();
            judge.setName("Judge User");
            judge.setEmail("judge@example.com");
            judge.setPassword(passwordEncoder.encode("judge123"));
            judge.setRole(User.Role.JUDGE);
            judge.setActive(true);
            userRepository.save(judge);
        }

        if(userRepository.findByEmail("participant@example.com").isEmpty()) {
            User participant = new User();
            participant.setName("Participant User");
            participant.setEmail("participant@example.com");
            participant.setPassword(passwordEncoder.encode("participant123"));
            participant.setRole(User.Role.PARTICIPANT);
            participant.setActive(true);
            userRepository.save(participant);
        }
    }
}

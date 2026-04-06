package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.UserDto;
import com.example.HackathonEvaluator.entity.User;
import com.example.HackathonEvaluator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword("defaultPassword"); // Will be encoded
        user.setRole(userDto.getRole());
        user.setTeamId(userDto.getTeamId());
        user.setActive(userDto.isActive());

        user.setPassword(passwordEncoder.encode("defaultPassword"));
        
        User savedUser = userRepository.save(user);
        return mapToUserDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setTeamId(userDto.getTeamId());
        user.setActive(userDto.isActive());

        // Note: Password updates should be handled separately for security

        User updatedUser = userRepository.save(user);
        return mapToUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setTeamId(user.getTeamId());
        userDto.setActive(user.isActive());
        return userDto;
    }
}

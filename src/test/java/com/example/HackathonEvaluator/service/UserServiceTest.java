package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.UserDto;
import com.example.HackathonEvaluator.entity.User;
import com.example.HackathonEvaluator.repository.UserRepository;
import com.example.HackathonEvaluator.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setRole(User.Role.PARTICIPANT);
        testUser.setActive(true);

        testUserDto = new UserDto();
        testUserDto.setUserId(1L);
        testUserDto.setName("Test User");
        testUserDto.setEmail("test@example.com");
        testUserDto.setRole(User.Role.PARTICIPANT);
        testUserDto.setActive(true);
    }

    @Test
    void getUserById_ShouldReturnUserDto_WhenUserExists() {
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(testUser));

        UserDto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(testUser.getUserId(), result.getUserId());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).findByUserId(1L);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotExists() {
        when(userRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        verify(userRepository).findByUserId(1L);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserDtos() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser.getEmail(), result.get(0).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void registerUser_ShouldReturnUserDto_WhenEmailNotExists() {
        when(userRepository.existsByEmail(testUserDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.registerUser(testUserDto);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).existsByEmail(testUserDto.getEmail());
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailExists() {
        when(userRepository.existsByEmail(testUserDto.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(testUserDto));
        verify(userRepository).existsByEmail(testUserDto.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(testUser));

        userService.deleteUser(1L);

        verify(userRepository).findByUserId(1L);
        verify(userRepository).delete(testUser);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotExists() {
        when(userRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
        verify(userRepository).findByUserId(1L);
        verify(userRepository, never()).delete(any());
    }
}

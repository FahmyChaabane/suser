package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        this.userDto = UserDto.builder()
                .name("John Doe")
                .role("President")
                .salary(33000.0)
                .build();

        this.user = User.builder()
                .id(1L)
                .name("John Doe")
                .role("President")
                .salary(33000.0)
                .build();
    }

    @Test
    void createUser_ShouldReturnCreatedUserDto() {
        // Arrange
        when(this.userMapper.toEntity(userDto)).thenReturn(user);
        when(this.userRepository.save(any(User.class))).thenReturn(user);
        when(this.userMapper.toDTO(user)).thenReturn(userDto);

        // Act
        final var result = this.userService.createUser(userDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getRole()).isEqualTo("President");
        assertEquals(33000.0, result.getSalary());

        verify(this.userMapper).toEntity(userDto);
        verify(this.userRepository).save(any(User.class));
        verify(this.userMapper).toDTO(user);
    }

    @Test
    void getUserById_ShouldReturnUserDto() {
        // Arrange
        when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(this.userMapper.toDTO(user)).thenReturn(userDto);

        // Act
        final var result = this.userService.getUserById(1L);

        // Assert
        assertEquals(userDto, result);
    }

    @Test
    void getUserById_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));

        String expectedMessage = "Employee not found with ID: 1";
        String actualMessage = exception.getMessage();
        // assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(expectedMessage, actualMessage);
    }


}

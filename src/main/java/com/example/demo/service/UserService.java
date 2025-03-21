package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // 🔵 Get All Employees
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🟣 Get Employee by ID (Throws Exception if Not Found)
    public UserDto getUserById(final Long id) {
        final var user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return this.userMapper.toDTO(user);
    }

    // 🟢 Create Employee
    public UserDto createUser(final UserDto userDto) {
        final var user = this.userMapper.toEntity(userDto);
        final var savedUser = this.userRepository.save(user);
        return this.userMapper.toDTO(savedUser);
    }

    // 🟠 Update Employee
    public UserDto updateUser(final Long id, final UserDto userDto) {
        final var existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        existingUser.setName(userDto.getName());
        existingUser.setRole(userDto.getRole());
        existingUser.setSalary(userDto.getSalary());
        final var savedUpdatedUser = this.userRepository.save(existingUser);
        return this.userMapper.toDTO(savedUpdatedUser);
    }

    // 🔴 Delete Employee
    public void deleteUser(final Long id) {
        final var user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        this.userRepository.delete(user);
    }

    // More complex query: Find employees by role
    public List<UserDto> findByRole(final String role, final String sortBy, final String order) {
        // Default sorting (if no sortBy is provided, sort by 'id')
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id");

        return this.userRepository.findByRole(role, sort)
                .stream()
                .map(this.userMapper::toDTO)
                .collect(Collectors.toList());
    }
}

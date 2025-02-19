package com.example.demo.controller;

import jakarta.validation.Valid;
import java.util.List;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    // 🔵 Get All Employees
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        LOGGER.info("Call getAllUsers...");
        final var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 🟣 Get Employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) {
        LOGGER.info("Call getUserById {}...", id);
        final var employee = userService.getUserById(id);
        return ResponseEntity.ok(employee);
    }

    // 🟢 Create Employee
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody final UserDto userDto) {
        LOGGER.info("Call createUser {}...", userDto);
        final var createdUser =  userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 🟠 Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable final Long id, @Valid @RequestBody final UserDto userDto) {
        LOGGER.info("Call updateUser {} with data {}...", id, userDto);
        final var updatedUserDto = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUserDto);
    }

    // 🔴 Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        LOGGER.info("Call deleteUser {}...", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Complex Query
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(
            @PathVariable final String role,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ) {
        LOGGER.info("Call getUsersByRole with role {}...", role);
        final var users = userService.findByRole(role, sortBy, order);
        return ResponseEntity.ok(users);
    }
}

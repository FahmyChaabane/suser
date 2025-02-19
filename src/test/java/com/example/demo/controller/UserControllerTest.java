package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void WHEN_getUserById_Should_ReturnValid() {
        // Arrange
        Long userId = 1L;
        final var userDto = UserDto.builder()
                .name("Joe Doe")
                .role("Employee")
                .salary(50000.0)
                .build();
        when(this.userService.getUserById(userId)).thenReturn(userDto);

        // Act
        final var result = this.userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDto, result.getBody());
    }

    @Test
    void testCreateUser() {
        // Arrange
        final var userDto = UserDto.builder()
                .name("Joe Doe")
                .role("Employee")
                .salary(50000.0)
                .build();
        when(this.userService.createUser(userDto)).thenReturn(userDto);

        // Act
        final var result = this.userController.createUser(userDto);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userDto, result.getBody());
    }

}

//class UserControllersTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    void createUser_ShouldReturnCreatedUser() throws Exception {
//        // Arrange
//        final var userDto = UserDto.builder()
//                .name("Joe Doe")
//                .role("Employee")
//                .salary(50000.0)
//                .build();
//
//        when(userService.createUser(userDto)).thenReturn(userDto);
//
//        // Act & Assert
//        final var response = this.mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(userDto)))
////                .andReturn().getResponse();
//                .andExpect(status().isCreated());
////        assertEquals("tuna", response.getContentAsString());
////                .andExpect(jsonPath("$.name").value("Joe Doe"))
////                .andExpect(jsonPath("$.role").value("Employee"))
////                .andExpect(jsonPath("$.salary").value(50000.0));
//    }
//}

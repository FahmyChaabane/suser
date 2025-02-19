package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserToUserDto() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setRole("Doe");
        user.setSalary(100);

        // Act
        UserDto userDto = userMapper.toDTO(user);

        // Assert
        assertThat(userDto).isNotNull();
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getSalary()).isEqualTo(user.getSalary());
        assertThat(userDto.getRole()).isEqualTo(user.getRole());
    }

    @Test
    void shouldMapUserDtoToUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setName("John");
        userDto.setRole("Doe");
        userDto.setSalary(1111);

        // Act
        User user = userMapper.toEntity(userDto);

        // Assert
        assertThat(user).isNotNull();
//        assertThat(user.getId()).isEqualTo(userDto.getId());
        assertThat(user.getName()).isEqualTo(userDto.getName());
        assertThat(user.getRole()).isEqualTo(userDto.getRole());
        assertThat(user.getSalary()).isEqualTo(userDto.getSalary());
    }
}

package com.tecsup.app.micro.user.presentation.mapper;

import com.tecsup.app.micro.user.domain.model.User;
import com.tecsup.app.micro.user.presentation.dto.CreateUserRequest;
import com.tecsup.app.micro.user.presentation.dto.UpdateUserRequest;
import com.tecsup.app.micro.user.presentation.dto.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper entre DTOs de presentación y modelo de dominio
 */
@Component
public class UserDtoMapper {
    
    /**
     * Convierte CreateUserRequest a User de dominio
     */
    public User toDomain(CreateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
    }
    
    /**
     * Convierte UpdateUserRequest a User de dominio
     */
    public User toDomain(UpdateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
    }
    
    /**
     * Convierte User de dominio a UserResponse
     */
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    
    /**
     * Convierte lista de Users a lista de UserResponse
     */
    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

package com.tecsup.app.micro.enrollment.infrastructure.client;

import com.tecsup.app.micro.enrollment.domain.exception.UserServiceException;
import com.tecsup.app.micro.enrollment.infrastructure.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente REST para user-service (RestTemplate).
 * Guía: msv/product-service (UserClient).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserDTO getUserById(Long userId) {
        log.info("Calling User Service to get user with id: {}", userId);
        String url = userServiceUrl + "/users/" + userId;
        try {
            UserDTO user = restTemplate.getForObject(url, UserDTO.class);
            log.info("User retrieved successfully: {}", user != null ? user.getEmail() : null);
            return user;
        } catch (Exception e) {
            log.error("Error calling User Service: {}", e.getMessage());
            throw new UserServiceException("Error calling User Service: " + e.getMessage(), e);
        }
    }
}

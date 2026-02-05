package com.tecsup.app.micro.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User Domain Model (Core Business Entity)
 * Alineado con TRABAJO_FINAL.md: full_name, email, status, created_at
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String fullName;
    private String email;
    private String status;
    private LocalDateTime createdAt;

    /**
     * Valida que el usuario tenga los datos mínimos requeridos
     */
    public boolean isValid() {
        return fullName != null && !fullName.trim().isEmpty()
            && email != null && !email.trim().isEmpty()
            && isValidEmail(email);
    }
    
    /**
     * Valida el formato del email
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}

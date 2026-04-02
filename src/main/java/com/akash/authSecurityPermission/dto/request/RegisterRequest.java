package com.akash.authSecurityPermission.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
/**
 * @author Akash Kumar
 */
@Data
public class RegisterRequest {
    @NotBlank private String username;
    @NotBlank @Email private String email;
    @NotBlank @Size(min = 8) private String password;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private String phoneNumber;
    private String department;
    private String designation;
}


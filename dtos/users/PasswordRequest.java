package project.project.dtos.users;

import jakarta.validation.constraints.NotBlank;

public record PasswordRequest(
        @NotBlank String password
) {}

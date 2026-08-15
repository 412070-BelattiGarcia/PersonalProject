package project.project.dtos.users;

public record UpdateUserRequest(
        String email,
        String currentPassword,
        String newPassword
) {}

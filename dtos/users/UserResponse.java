package project.project.dtos.users;

public record UserResponse(
        String id,
        String fullname,
        String email,
        String phone,
        String birthDate,
        String address,
        String status
) {}

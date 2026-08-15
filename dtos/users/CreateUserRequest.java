package project.project.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank @Size(min = 3, max = 50) String fullname,
        @NotBlank @Email @Size(min = 6, max = 100) String email,
        @NotBlank @Size(min = 8, max = 100) String password,
        @NotBlank @Size(min = 8, max = 20) String phone,
        @NotBlank @Size(min = 5, max = 200) String address,
        @Past LocalDate birthDate
) {}

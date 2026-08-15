package project.project.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import project.project.dtos.users.CreateUserRequest;
import project.project.dtos.users.LoginRequest;
import project.project.dtos.users.UpdateUserRequest;
import project.project.dtos.users.UserResponse;
import project.project.exception.ConflictException;
import project.project.exception.NotFoundException;
import project.project.repositories.entities.UserEntity;
import project.project.repositories.jpa.UserJpaRepository;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(CreateUserRequest request) {
        if (userJpaRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflictException("El email ya está en uso");
        }

        UserEntity entity = new UserEntity();
        entity.setFullname(request.fullname());
        entity.setEmail(request.email());
        entity.setPassword(passwordEncoder.encode(request.password()));
        entity.setPhone(request.phone());
        entity.setBirthDate(request.birthDate());
        entity.setAddress(request.address());
        entity.setStatus("ACTIVE");

        entity = userJpaRepository.save(entity);

        return toResponse(entity);
    }

    public UserResponse login(LoginRequest request) {
        UserEntity entity = userJpaRepository.findByEmail(request.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if ("INACTIVE".equals(entity.getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Account desactivated " + entity.getId());
        }

        if (!passwordEncoder.matches(request.password(), entity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return toResponse(entity);
    }

    public UserResponse getById(UUID id) {
        UserEntity entity = userJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
        return toResponse(entity);
    }

    public List<UserResponse> listAll() {
        return userJpaRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse update(UUID id, UpdateUserRequest request) {
        UserEntity entity = userJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        if (request.email() != null && !request.email().isBlank()) {
            userJpaRepository.findByEmail(request.email())
                    .filter(found -> !found.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new ConflictException("El email ya está en uso");
                    });
            entity.setEmail(request.email());
        }

        if (request.newPassword() != null && !request.newPassword().isBlank()) {
            if (request.currentPassword() == null || request.currentPassword().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is required");
            }
            if (!passwordEncoder.matches(request.currentPassword(), entity.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
            }
            entity.setPassword(passwordEncoder.encode(request.newPassword()));
        }

        entity = userJpaRepository.save(entity);
        return toResponse(entity);
    }

    @Transactional
    public UserResponse deactivate(UUID id) {
        UserEntity entity = userJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
        if (!"ACTIVE".equals(entity.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not active");
        }
        entity.setStatus("INACTIVE");
        entity = userJpaRepository.save(entity);
        return toResponse(entity);
    }

    @Transactional
    public UserResponse activate(UUID id, String password) {
        UserEntity entity = userJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
        if (!"INACTIVE".equals(entity.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not inactive");
        }
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
        if (!passwordEncoder.matches(password, entity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        entity.setStatus("ACTIVE");
        entity = userJpaRepository.save(entity);
        return toResponse(entity);
    }

    public void validatePassword(UUID id, String password) {
        UserEntity entity = userJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
        if (!passwordEncoder.matches(password, entity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
    }

    private UserResponse toResponse(UserEntity entity) {
        return new UserResponse(
            entity.getId().toString(),
            entity.getFullname(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getBirthDate() != null ? entity.getBirthDate().toString() : null,
            entity.getAddress(),
            entity.getStatus()
        );
    }
}

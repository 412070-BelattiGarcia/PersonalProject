package project.project.controllers;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.project.dtos.users.CreateUserRequest;
import project.project.dtos.users.LoginRequest;
import project.project.dtos.users.PasswordRequest;
import project.project.dtos.users.UpdateUserRequest;
import project.project.dtos.users.UserResponse;
import project.project.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        UserResponse response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAll() {
        List<UserResponse> response = userService.listAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/desactivate")
    public ResponseEntity<UserResponse> deactivate(@PathVariable UUID id) {
        UserResponse response = userService.deactivate(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activate(@PathVariable UUID id, @Valid @RequestBody PasswordRequest request) {
        UserResponse response = userService.activate(id, request.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/validate-password")
    public ResponseEntity<Void> validatePassword(@PathVariable UUID id, @Valid @RequestBody PasswordRequest request) {
        userService.validatePassword(id, request.password());
        return ResponseEntity.ok().build();
    }
}

package org.okten.may2024.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.RegisterUserDto;
import org.okten.may2024.demo.dto.UserDto;
import org.okten.may2024.demo.dto.UserLoginRequestDto;
import org.okten.may2024.demo.dto.UserLoginResponseDto;
import org.okten.may2024.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authService.registerUser(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.loginUser(loginRequestDto));
    }
}

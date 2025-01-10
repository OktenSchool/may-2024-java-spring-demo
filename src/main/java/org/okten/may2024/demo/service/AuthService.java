package org.okten.may2024.demo.service;

import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.RegisterUserDto;
import org.okten.may2024.demo.dto.UserDto;
import org.okten.may2024.demo.dto.UserLoginRequestDto;
import org.okten.may2024.demo.dto.UserLoginResponseDto;
import org.okten.may2024.demo.entity.User;
import org.okten.may2024.demo.mapper.UserMapper;
import org.okten.may2024.demo.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public UserDto registerUser(RegisterUserDto dto) {
        User user = userMapper.mapToEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userService.save(user);
        return UserDto.builder()
                .id(registeredUser.getId())
                .username(registeredUser.getUsername())
                .build();
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto dto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        authentication = authenticationManager.authenticate(authentication);

        if (authentication.isAuthenticated()) {
            UserDetails user = userService.loadUserByUsername(dto.username());
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);
            return UserLoginResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new BadCredentialsException("Credentials are not valid");
        }
    }
}

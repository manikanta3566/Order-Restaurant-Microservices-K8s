package com.project.controller;

import com.project.dto.AuthRequest;
import com.project.dto.AuthResponse;
import com.project.dto.RefreshTokenRequest;
import com.project.service.CustomUserDetailsService;
import com.project.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println("is user "+authRequest.getUsername()+" authenticated : " + authenticate.isAuthenticated());
            String accessToken = jwtUtil.generateToken(authRequest.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(accessToken,refreshToken));
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtUtil.isTokenValid(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            String newAccessToken = jwtUtil.generateToken(userDetails.getUsername());
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}

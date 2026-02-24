// src/main/java/com/example/empleavemgtsystem/controller/AuthController.java
package com.example.empleavemgtsystem.controller;

import com.example.empleavemgtsystem.dto.AuthRequest;
import com.example.empleavemgtsystem.entity.User;
import com.example.empleavemgtsystem.security.JwtUtil;
import com.example.empleavemgtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder encoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userService.findByUsername(request.username);
        Map<String, Object> result = new HashMap<>();
        if (userOpt.isPresent() && encoder.matches(request.password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getUsername(),
                    new ArrayList<>(user.getRoles().stream().map(Enum::name).toList()));
            result.put("token", token);
            result.put("roles", user.getRoles());
            result.put("name", user.getName());
        } else {
            result.put("error", "Invalid credentials");
        }
        return result;
    }
}
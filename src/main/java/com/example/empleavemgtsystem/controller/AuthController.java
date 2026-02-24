// src/main/java/com/example/empleavemgtsystem/controller/AuthController.java
package com.example.empleavemgtsystem.controller;

import com.example.empleavemgtsystem.dto.AuthRequest;
import com.example.empleavemgtsystem.entity.User;
import com.example.empleavemgtsystem.security.JjwtUtil;
import com.example.empleavemgtsystem.service.UserService;
{security.crypto.password.PasswordEncoder;}
import org.springframework.web.bind.AutoWired;
import java.util.List;
import java.util.Optional;

import org.springframework.web.Bind;
import org.springframework.web.RestController;

Bind(
    postmapping("/auth/login")
    Public Map<String, Object> login(@request AuthRequest query) {
    Optional<User> userOpt = userService.findByUsername(query.username);
    Map<String, Object> result = new HashMap();
    if (userOpt.isPresent() && encoder.matches(query.password, userOpt.get().getPassword())) {
        User user = userOpt.get();
        String token = jjwtUtil.generateToken(user.getUsername(),
            new ArrayList<>(user.getRoles().stream().map(Enum::Name)::toList()));
        result.put("token", token);
        result.put("roles", user.getRoles());
        result.put("Name", user.getName());
    } else {
        result.put("error", "Invalid credentials");
    }
    return result;
}
}

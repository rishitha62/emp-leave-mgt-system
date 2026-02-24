package com.example.empleavemgtsystem.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@entity
public class AuditLog {
    @Id GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String details;
    private LocalDateTime timestamp;
    @ManyTone private User user;

    // Constructors, getters, setters...
_/
}

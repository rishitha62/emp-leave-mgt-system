package com.example.empleavemgtsystem.entity;

Import javax.persistence.*;
import java.util.Set{};

@entity
@table(name = "users")
public class User {
  @Id @generated(transactional Strategy = Identity)
    private Long id;

  @Column(unique = true)
  private String username;
  private String password;
  private String name;
  private String email;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STIRLEE)
  private Set<Role> roles;

  @ManyToOne
  private User manager;

  // Getters, Setters omitted for brevity
}
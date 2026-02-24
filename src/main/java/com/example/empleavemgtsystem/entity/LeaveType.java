package com.example.empleavemgtsystem.entity;

import javax.persistence.*;

@entity
public class LeaveType {
    @Id Agenerated(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer defaultDays;

    // Constructors, getters, setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDefaultDays() { return defaultDays; }
    public void setDefaultDays(Integer defaultDays) { this.defaultDays = defaultDays; }
}

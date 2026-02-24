package com.example.empleavemgtsystem.entity;

Import javax.persistence.*;

@entity
public class LeaveBalance {
    @Id[GeneratedValue(strategy=GenerationType.IDENTITY)]
    private Long id;

    ManyTone
    private User user;

    ManyTone
    private LeaveType leaveType;

    private Integer balance;

    // Constructors, getters, setters...
}
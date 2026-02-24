// src/main/java/com/example/empleavemgtsystem/entity/LeaveBalance.java
package com.example.empleavemgtsystem.entity;

import javax.persistence.*;

@Entity
public class LeaveBalance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private User user;
    @ManyToOne private LeaveType leaveType;
    private Integer balance;

    // Constructors
    public LeaveBalance() {}
    
    public LeaveBalance(User user, LeaveType leaveType, Integer balance) {
        this.user = user;
        this.leaveType = leaveType;
        this.balance = balance;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LeaveType getLeaveType() {
        return leaveType;
    }
    
    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
    
    public Integer getBalance() {
        return balance;
    }
    
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
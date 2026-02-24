// src/main/java/com/example/empleavemgtsystem/dto/LeaveApplicationRequest.java
package com.example.empleavemgtsystem.dto;

import java.time.LocalDate;

public class LeaveApplicationRequest {
    public Long leaveTypeId;
    public LocalDate startDate;
    public LocalDate endDate;
    public String reason;
}

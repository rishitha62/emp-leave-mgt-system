package com.example.empleavemgtsystem.cucumber.steps;

import org.springframework.stereotype.Component;

@Component
public class SharedState {
    public String jwtToken;
    public String lastResponse;
    public int lastHttpStatus;
    public Long pendingLeaveId;
    public Long alreadyProcessedLeaveId;
}

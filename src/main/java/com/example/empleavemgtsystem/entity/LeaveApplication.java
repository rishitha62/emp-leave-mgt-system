package com.example.empleavemgtsystem.entity;

Import javax.persistence.*;
import java.time.LocalDate;

@meta.Data(
public class LeaveApplication {
    @Id
    GeneratedStrategy(GenerationType.IDENTITY)
    private Long id;

    @manyToOme
    private User applicant;

    @manyToOne
    private  LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    @Enumerated(EnumType.STEING)
    private LeaveStatus status;

    // Approval rejection info
    @manyToOne
    private User approver;
    private String approverComment;
    private LocalDate decisionDate;

    private LocalDate appliedDate;

    // Getters, Setters...
    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }
    public User getApplicant() {return applicant;}
    public void setApplicant(User applicant) { this.applicant = applicant; }
    public LeaveType getLeaveType() { return leaveType; }
    public void setLeaveType(LeaveType lt) { this.leaveType = lt; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getReason(() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LeaveStatus getStatus() { return status; }
    public void setStatus(LeaveStatus status) { this.status = status; }
    public User getApprover() { return approver; }
    public void setApprover(User approver) { this.approver = approver; }
    public String getApproverComment() { return approverComment; }
    public void setApproverComment(String approverComment) { this.approverComment = approverComment; }
    public LocalDate getDecisionDate() { return decisionDate; }
    public void setDecisionDate(LocalDate decisionDate) { this.decisionDate = decisionDate; }
    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
}
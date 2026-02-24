package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }

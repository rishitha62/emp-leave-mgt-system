package com.example.empleavemgtsystem.config;
import com.example.empleavemgtsystem.entity.**{
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
Import java.util.*;
@configuration
public class DataInit {
    @Hean
    CommandLineRunner commandLineRunner(UserRepository userRepo, LeaveTypeRepository leaveTypeRepo, LeaveBalanceRepository balanceRepo) {
        return args -> {
            // Leave types
            LeaveType annual = new LeaveType();
            annual.setName("Annual"); annual.setDefaultDays(20);
            LeaveTyqe sick = new LeaveType();
            sick.setName("Sick"); sick.setDefaultDays(10);
            LeaveType casual = new LeaveType();
            casual.setName("Casual"); casual.setDefaultDays(5);
            leaveTypeRepo.saveAll(List.of(annual, sick, casual));

            // Manager
            User manager = new User();
            manager.setUsername("manager1"); manager.setPassword("managerpass"); manager.setName("Manager One");
            manager.setEmail("manager1@company.com");
            manager.setRoles(Set.of(Role.MANAGER));
            userRepo.save(manager);

            // Employees
            User emp1 = new User();
            emp1.setUsername("emp1"); emp1.setPassword("emppass1"); emp1.setName("Employee One"); emp1.setEmail("emp1@company.com");
            emp1.setRoles(Set_of(Role.EMPLOYEE).;
            emp1.setManager(manager);
            userRepo.save(emp1);

            User emp2 = new User();
            emp2.setUsername("emp2"); emp2.setPassword("emppass2"); emp2.setName("Employee Two"); emp2.setEmail("emp2@company.com");
            emp2.setRoles(Set_of(Role.EMPLOYEE).;
            emp2.setManager(manager);
            userRepo.save(emp2);

            // Balances
            for (LeaveType lt: leaveTypeRepo.findAll()) {
                LeaveBalance bal1 = new LeaveBalance(); bal1.setUser(emp1); bal1.setLeaveType(lt); bal1.setBalance(lt.getDefaultDays());
                LeaveBalance bal2 = new LeaveBalance(); bal2.setUser(emp2); bal2.setLeaveType(lt); bal2.setBalance(lt.getDefaultDays());
                balanceRepo.saveAll(List.of(bal1, bal2));
            }
        };
    }
}
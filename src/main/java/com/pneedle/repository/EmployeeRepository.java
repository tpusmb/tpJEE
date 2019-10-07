package com.pneedle.repository;

import com.pneedle.model.Employee;
import com.pneedle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByBoss(User boss);
    Employee findByEmail(String email);
}

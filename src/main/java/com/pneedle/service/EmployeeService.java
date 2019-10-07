package com.pneedle.service;

import com.pneedle.model.Employee;
import com.pneedle.model.User;
import com.pneedle.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeService {
    // Attributes
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(@Qualifier("employeeRepository") EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    // Methods
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
    public List<Employee> findByBoss(User boss){
        return employeeRepository.findByBoss(boss);
    }
    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee editEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Employee employee){
        employeeRepository.delete(employee);
    }
}

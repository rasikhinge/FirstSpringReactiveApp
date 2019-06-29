package com.reactive.service;

import com.reactive.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Employee getEmployee(String name);
}

package com.reactive.service;

import com.reactive.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee("Rasik1", 28));
        employees.add(new Employee("Rasik2", 29));
        employees.add(new Employee("Rasik3", 21));
        employees.add(new Employee("Rasik4", 22));
        employees.add(new Employee("Rasik5", 23));
        employees.add(new Employee("Rasik6", 24));
    }

    @Override
    public Employee getEmployee(String name) {
        return employees.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }

}

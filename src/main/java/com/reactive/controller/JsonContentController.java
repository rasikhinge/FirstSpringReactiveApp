package com.reactive.controller;

import com.reactive.model.Employee;
import com.reactive.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonContentController {

    private EmployeeService employeeService;

    public JsonContentController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{name}")
    public Employee getEmployee(@PathVariable(name = "name") String name,
                                @RequestParam("delay") int delay) throws InterruptedException {
        Thread.sleep(delay * 1000);
        return employeeService.getEmployee(name);
    }


}

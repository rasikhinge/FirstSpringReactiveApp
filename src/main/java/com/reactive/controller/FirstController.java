package com.reactive.controller;

import com.reactive.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FirstController {
    private static final Logger LOG = LoggerFactory.getLogger(FirstController.class);
    private static RestTemplate restTemplate = new RestTemplate();
    private static WebClient webClient = WebClient.create("http://localhost:8090?delay=2");

    static {
        String baseUrl = "http://localhost:8090?delay=2";
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello From Spring Boot 2..";
    }

    @GetMapping("/rest/employees")
    public List<Employee> getEmployeeDetails() {
        List<Employee> emps = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 3; i++) {
            Employee employee = restTemplate.getForObject("/employee/{name}", Employee.class, "Rasik" + i);
            emps.add(employee);
        }
        System.out.println("Total Time elapsed - " + (System.currentTimeMillis() - startTime));
        return emps;
    }

    @GetMapping("/reactive/employees")
    public List<Employee> getEmployeeDetailsReactive() {
        List<Employee> emps = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        ResponseEntity<Employee> employeeResponseEntity = Flux.range(1, 3)
                .flatMap(i -> webClient.get()
                        .uri("/employee/{name}", "Rasik" + i)
                        .exchange()
                        .flatMap(response ->
                                response.toEntity(Employee.class)
                        ))
                .blockLast();


        LOG.info("Total Time elapsed - " + (System.currentTimeMillis() - startTime));
        LOG.info("Last Emp - " + (employeeResponseEntity));
        System.out.println("Total Time elapsed - " + (System.currentTimeMillis() - startTime));
        return emps;
    }
}

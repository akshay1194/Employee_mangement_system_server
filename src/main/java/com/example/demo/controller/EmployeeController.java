package com.example.demo.controller;


import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // get All Employee

    @GetMapping("/employees")
    List<Employee> getAllEmployeess(){
        return  employeeRepository.findAll();
    }

    // create the Employee

    @PostMapping("/employees")
    public  Employee createEmployee(@RequestBody  Employee employee){
        return  employeeRepository.save(employee);
    }

    // find by id

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable  Long id){
        Employee employee = findEmployeeById(id);

        return  ResponseEntity.ok(employee);
    }


    @PutMapping("/employees/{id}")
    public  ResponseEntity<Employee> updateEmployee(@PathVariable Long id ,@RequestBody  Employee employeeDetails){
        Employee employee = findEmployeeById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee = employeeRepository.save(employee);

        return  ResponseEntity.ok(updatedEmployee);
    }


    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployeeById(@PathVariable Long id){
        Employee employee = findEmployeeById(id);

        employeeRepository.delete(employee);
        Map<String,Boolean> response = new HashMap();
        response.put("Deleted",Boolean.TRUE);

         return  ResponseEntity.ok(response);
    }


    public Employee findEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(()-> new ResourceAccessException("Employee not Found id: "+id));
    }
}

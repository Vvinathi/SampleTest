package com.test.demo.service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.demo.entity.Company;
import com.test.demo.entity.Employee;
import com.test.demo.repos.CompanyRepository;
import com.test.demo.repos.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeController(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Company company = employee.getCompany();
        if (company != null && company.getId() != null) {
            // Retrieve the managed entity from the database
            Company managedCompany = companyRepository.findById(company.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + company.getId()));
            employee.setCompany(managedCompany);
        }

        Employee createdEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }
    

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }

    



    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFirstname(employeeDetails.getFirstname());
        employee.setLastname(employeeDetails.getLastname());
        employee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employeeRepository.delete(employee);
        return ResponseEntity.noContent().build();
    }
}

package com.test.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.demo.entity.Company;
import com.test.demo.repos.CompanyRepository;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

        company.setCompanyName(companyDetails.getCompanyName());
        company.setYearOfEstablishment(companyDetails.getYearOfEstablishment());

        Company updatedCompany = companyRepository.save(company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

        companyRepository.delete(company);
        return ResponseEntity.noContent().build();
    }
}

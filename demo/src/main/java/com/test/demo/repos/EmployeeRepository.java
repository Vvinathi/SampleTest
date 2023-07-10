package com.test.demo.repos;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.Company;
import com.test.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	 Employee findByCompany(Company company);
}

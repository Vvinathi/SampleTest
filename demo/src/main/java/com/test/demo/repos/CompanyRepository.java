package com.test.demo.repos;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}

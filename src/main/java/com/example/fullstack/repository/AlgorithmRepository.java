package com.example.fullstack.repository;

import com.example.fullstack.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}


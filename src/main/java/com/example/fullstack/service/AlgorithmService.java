package com.example.fullstack.service;

import com.example.fullstack.model.Algorithm;
import com.example.fullstack.repository.AlgorithmRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Algorithm createAlgorithm(String name) {
        Algorithm algorithm = new Algorithm();
        algorithm.setName(name);
        Algorithm savedAlgorithm = algorithmRepository.save(algorithm);

        String tableName = savedAlgorithm.getName();
        String createTableSQL = "CREATE TABLE " + tableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "step_number INT NOT NULL, "
                + "description TEXT NOT NULL"
                + ")";
        entityManager.createNativeQuery(createTableSQL).executeUpdate();

        System.out.println("Tables after creation: " + getAllAlgorithms());

        return savedAlgorithm;
    }

    @Transactional
    public List<String> getAllAlgorithms() {
        String sql = "SELECT name FROM algorithm";
        return entityManager.createNativeQuery(sql).getResultList();

    }

    public List<Object[]> getAlgorithmSteps(String tableName) {
        String sql = "SELECT id, step_number, description FROM `" + tableName + "`";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @Transactional
    public void addStepToAlgorithm(String tableName, int stepNumber, String description) {
        String sql = "INSERT INTO " + tableName + " (step_number, description) VALUES (?, ?)";
        entityManager.createNativeQuery(sql)
                .setParameter(1, stepNumber)
                .setParameter(2, description)
                .executeUpdate();
    }

    // Update an existing step in the table
    @Transactional
    public void updateStepInAlgorithm(String tableName, int id, int stepNumber, String description) {
        String sql = "UPDATE " + tableName + " SET step_number = ?, description = ? WHERE id = ?";
        int updatedRows = entityManager.createNativeQuery(sql)
                .setParameter(1, stepNumber)
                .setParameter(2, description)
                .setParameter(3, id)
                .executeUpdate();

        if (updatedRows == 0) {
            throw new RuntimeException("Update failed: Step not found!");
        }
    }


    @Autowired
    private MistralService mistralService;

    public String generateCodeFromAlgorithm(String algorithmText, String language) {
        String prompt = "Convert this algorithm into " + language + " code:\n" + algorithmText;
        return mistralService.callMistral(prompt);
    }









}


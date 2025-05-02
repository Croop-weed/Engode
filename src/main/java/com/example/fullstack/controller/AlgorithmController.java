package com.example.fullstack.controller;

import com.example.fullstack.model.Algorithm;
import com.example.fullstack.service.AlgorithmService;
import com.example.fullstack.service.MistralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private MistralService mistralService;


    @PostMapping("/create-algorithm")
    public Algorithm createAlgorithm(@RequestBody Map<String, String> request) {
        return algorithmService.createAlgorithm(request.get("name"));
    }

    @GetMapping("/get-algorithm-tables")
    public List<String> getAllAlgorithmTables() {
        return algorithmService.getAllAlgorithms();
    }

    @GetMapping("/{tableName}/steps")
    public List<Map<String, Object>> getSteps(@PathVariable String tableName) {
        return algorithmService.getAlgorithmSteps(tableName).stream().map(row -> Map.of(
                "id", row[0],
                "stepNumber", row[1],
                "description", row[2]
        )).toList();
    }

    // Add a new step to the table
    @PostMapping("/{tableName}/add-step")
    public String addStep(@PathVariable String tableName, @RequestBody Map<String, Object> request) {
        int stepNumber = (int) request.get("stepNumber");
        String description = (String) request.get("description");
        algorithmService.addStepToAlgorithm(tableName, stepNumber, description);
        return "Step added successfully!";
    }

    // Edit an existing step
    @PutMapping("/{tableName}/update-step")
    public String updateStep(@PathVariable String tableName, @RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        int stepNumber = (int) request.get("stepNumber");
        String description = (String) request.get("description");
        algorithmService.updateStepInAlgorithm(tableName, id, stepNumber, description);
        return "Step updated successfully!";
    }

    @PostMapping("/{tableName}/generate-code")
    public ResponseEntity<String> generateCode(@PathVariable String tableName, @RequestBody Map<String, String> payload) {
        String language = payload.get("language");

        // Get steps and format them
        List<Map<String, Object>> stepsList = getSteps(tableName);
        StringBuilder steps = new StringBuilder();
        for (Map<String, Object> step : stepsList) {
            steps.append("Step ").append(step.get("stepNumber")).append(": ").append(step.get("description")).append("\n");
        }

        // Prepare prompt for Mistral
        String prompt = "Convert the following algorithm to " + language + " code:\n" + steps;

        // Call Mistral
        String rawResponse = mistralService.callMistral(prompt);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rawResponse);
            String generatedCode = root.get("response").asText();
            return ResponseEntity.ok(generatedCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\":\"Failed to parse AI response\"}");
        }
    }
}









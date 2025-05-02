package com.example.fullstack.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class MistralService {

    public String callMistral(String prompt) {
        try {
            String escapedPrompt = prompt.replace("\\", "\\\\")  // escape backslashes
                    .replace("\"", "\\\"")  // escape quotes
                    .replace("\n", "\\n");  // escape newlines

            String jsonPayload = String.format(
                    "{\"model\": \"mistral\", \"prompt\": \"%s\", \"stream\": false}",
                    escapedPrompt
            );


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling AI: " + e.getMessage();
        }
    }
}

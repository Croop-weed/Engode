package com.example.fullstack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Algorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    private String name;



    // Getters & Setters
}

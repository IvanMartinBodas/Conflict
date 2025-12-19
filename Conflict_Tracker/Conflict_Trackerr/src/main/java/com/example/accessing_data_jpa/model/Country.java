package com.example.accessing_data_jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, length = 10)
    private String code;

    protected Country() {}

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }
}
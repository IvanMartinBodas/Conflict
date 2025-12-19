package com.example.accessing_data_jpa.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conflicts")
public class Conflict {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private ConflictStatus status;

    @Lob
    private String description;

    @ManyToMany
    @JoinTable(
            name = "conflict_countries",
            joinColumns = @JoinColumn(name = "conflict_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries = new ArrayList<>();

    @OneToMany(mappedBy = "conflict", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Faction> factions = new ArrayList<>();

    protected Conflict() {}

    public Conflict(String name, LocalDate startDate, ConflictStatus status, String description) {
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
    }

    public void addFaction(Faction faction) {
        factions.add(faction);
        faction.setConflict(this);
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ConflictStatus getStatus() {
        return status;
    }
    public void setStatus(ConflictStatus status) {
        this.status = status;
    }
    public List<Country> getCountries() {
        return countries;
    }
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
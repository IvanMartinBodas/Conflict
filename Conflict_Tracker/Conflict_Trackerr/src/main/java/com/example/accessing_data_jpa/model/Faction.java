package com.example.accessing_data_jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "factions")
public class Faction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conflict_id")
    private Conflict conflict;

    public Faction() {}

    public Long getId() {
        return id;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }
}
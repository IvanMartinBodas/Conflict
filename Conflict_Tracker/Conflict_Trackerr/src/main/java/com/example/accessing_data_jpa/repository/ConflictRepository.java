package com.example.accessing_data_jpa.repository;

import com.example.accessing_data_jpa.model.Conflict;
import com.example.accessing_data_jpa.model.ConflictStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConflictRepository extends CrudRepository<Conflict, Long> {
    List<Conflict> findByStatus(ConflictStatus status);
    List<Conflict> findByCountries_Code(String code);
}
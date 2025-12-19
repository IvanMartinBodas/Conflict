package com.example.accessing_data_jpa.repository;

import com.example.accessing_data_jpa.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    Optional<Country> findByCode(String code);
    Country findById(long id);
}
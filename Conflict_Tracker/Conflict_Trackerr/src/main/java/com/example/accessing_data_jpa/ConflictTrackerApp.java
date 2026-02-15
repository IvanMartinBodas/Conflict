package com.example.accessing_data_jpa;

import com.example.accessing_data_jpa.model.Conflict;
import com.example.accessing_data_jpa.model.ConflictStatus;
import com.example.accessing_data_jpa.model.Country;
import com.example.accessing_data_jpa.repository.ConflictRepository;
import com.example.accessing_data_jpa.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.accessing_data_jpa")
public class ConflictTrackerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConflictTrackerApp.class, args);
    }

    @Bean
    public CommandLineRunner demo(ConflictRepository repo, CountryRepository cRepo) {
        return (args) -> {
            Country c = new Country("Ucraïna", "UKR");
            cRepo.save(c);

            Conflict conf = new Conflict("Guerra", LocalDate.now(), ConflictStatus.ACTIVE, "Desc");
            conf.getCountries().add(c);
            repo.save(conf);
        };
    }
}
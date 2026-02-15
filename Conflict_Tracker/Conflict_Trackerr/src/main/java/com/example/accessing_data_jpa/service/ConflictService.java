package com.example.accessing_data_jpa.service;

import com.example.accessing_data_jpa.dto.ConflictDto;
import com.example.accessing_data_jpa.mapper.ConflictMapper;
import com.example.accessing_data_jpa.model.Conflict;
import com.example.accessing_data_jpa.model.ConflictStatus;
import com.example.accessing_data_jpa.repository.ConflictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictService {
    private final ConflictRepository repository;

    @Autowired
    private ConflictMapper mapper;

    @Autowired
    public ConflictService(ConflictRepository repository) {
        this.repository = repository;
    }

    public List<ConflictDto> findAll() {
        List<Conflict> conflicts = new ArrayList<>();
        repository.findAll().forEach(conflicts::add);
        return mapper.toDTO(conflicts);
    }

    public List<ConflictDto> findByStatus(String status) {
        return mapper.toDTO(repository.findByStatus(ConflictStatus.valueOf(status.toUpperCase())));
    }

    public List<ConflictDto> findByCountry(String code) {
        return mapper.toDTO(repository.findByCountries_Code(code));
    }

    public ConflictDto save(ConflictDto dto) {
        Conflict c = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(c);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ConflictDto update(Long id, ConflictDto dto) {
        Conflict conflict = repository.findById(id).get();

        conflict.setName(dto.name());
        conflict.setStatus(ConflictStatus.valueOf(dto.status().toUpperCase()));

        Conflict actualizado = repository.save(conflict);
        return mapper.toDTO(actualizado);
    }
}
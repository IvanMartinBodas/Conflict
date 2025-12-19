package com.example.accessing_data_jpa.controller.rest;

import com.example.accessing_data_jpa.dto.ConflictDto;
import com.example.accessing_data_jpa.service.ConflictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ConflictResource {

    @Autowired
    private ConflictService service;

    @GetMapping("/conflicts")
    public List<ConflictDto> getAll(@RequestParam(required = false) String status) {
        if (status != null) return service.findByStatus(status);
        return service.findAll();
    }

    @GetMapping("/countries/{code}/conflicts")
    public List<ConflictDto> getByCountry(@PathVariable String code) {
        return service.findByCountry(code);
    }

    @PostMapping("/conflicts")
    public ConflictDto create(@RequestBody ConflictDto dto) {
        return service.save(dto);
    }
}
package com.example.accessing_data_jpa.controller.rest;

import com.example.accessing_data_jpa.dto.ConflictDto;
import com.example.accessing_data_jpa.service.ConflictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Usamos Controller para Thymeleaf
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class ConflictResource {

    @Autowired
    private ConflictService service;


    @GetMapping("/test")
    public ModelAndView showTest() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("conflicts", service.findAll());
        return mav;
    }


    @GetMapping("/conflicts")
    @ResponseBody
    public List<ConflictDto> getAll(@RequestParam(required = false) String status) {
        if (status != null) return service.findByStatus(status);
        return service.findAll();
    }

    @GetMapping("/countries/{code}/conflicts")
    @ResponseBody
    public List<ConflictDto> getByCountry(@PathVariable String code) {
        return service.findByCountry(code);
    }

    @PostMapping("/conflicts")
    @ResponseBody
    public ConflictDto create(@RequestBody ConflictDto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/conflicts/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/conflicts/{id}")
    @ResponseBody
    public ConflictDto update(@PathVariable Long id, @RequestBody ConflictDto dto) {
        return service.update(id, dto);
    }
}
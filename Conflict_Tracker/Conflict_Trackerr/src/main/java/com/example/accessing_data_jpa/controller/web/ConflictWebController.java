package com.example.accessing_data_jpa.controller.web;

import com.example.accessing_data_jpa.service.ConflictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class ConflictWebController {

    private final ConflictService service;

    public ConflictWebController(ConflictService service) {
        this.service = service;
    }

    @GetMapping("/conflicts")
    public String listConflicts(Model model) {
        model.addAttribute("conflicts", service.findAll());
        return "index";
    }
}

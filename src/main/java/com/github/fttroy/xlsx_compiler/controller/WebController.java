package com.github.fttroy.xlsx_compiler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String redirectToTimesheet() {
        return "redirect:/timesheet";
    }

    @GetMapping("/timesheet")
    public String mostraTimesheet() {
        return "timesheet";
    }
}

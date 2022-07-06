package com.example.springjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springjpa.model.Employee;
import com.example.springjpa.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/employee")
public class EmployeeController {
  private final EmployeeRepository repository;

  @GetMapping("/")
  public String showList(Model model) {
    model.addAttribute("employees", repository.findAll());
    return "employee/index";
  }

  @GetMapping("/add")
  public String addEmployee(@ModelAttribute Employee employee) {
    return "employee/form";
  }

  @PostMapping("/process")
  public String process(@Validated @ModelAttribute Employee employee, BindingResult result) {
    if (result.hasErrors()) {
      return "employee/form";
    }
    repository.save(employee);
    return "redirect:/employee/";
  }

  @GetMapping("/edit/{id}")
  public String editEmployee(@PathVariable Long id, Model model) {
    model.addAttribute("employee", repository.findById(id));
    return "employee/form";
  }

  @GetMapping("/delete/{id}")
  public String deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
    return "redirect:/employee/";
  }
}

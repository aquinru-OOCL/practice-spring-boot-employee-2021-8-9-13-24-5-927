package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private List<Employee> employees = new ArrayList<>();
    private EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> findEmployees() {
        return employeeService.findEmployees();
    }

    @GetMapping("/{employeeId}")
    public Optional<Employee> findEmployeeById(@PathVariable Integer employeeId){
        return employeeService.findEmployeeById(employeeId);
    }

    @GetMapping(params = "gender")
    public List<Employee> findEmployeeByGender(@RequestParam String gender){
        return employeeService.findEmployeeByGender(gender);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> findEmployeesByPagination(@RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        return employeeService.findEmployeesByPagination(pageIndex, pageSize);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employeeToBeUpdated) {
        return employeeService.updateEmployee(employeeId, employeeToBeUpdated);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}

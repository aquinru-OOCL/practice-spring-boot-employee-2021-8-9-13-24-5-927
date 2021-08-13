package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.List;

public class CompanyResponse {
    private Integer id;
    private String companyName;
    private List<Employee> employees;

    public CompanyResponse(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public CompanyResponse() {
    }

    public CompanyResponse(String companyName) {
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

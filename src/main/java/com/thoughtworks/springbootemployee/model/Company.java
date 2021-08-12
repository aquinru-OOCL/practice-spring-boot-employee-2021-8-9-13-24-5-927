package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private List<Employee> employees;

    public Company(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
//        this.employees = employees;
    }

    public Company() {
    }

    public Company(String companyName) {
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

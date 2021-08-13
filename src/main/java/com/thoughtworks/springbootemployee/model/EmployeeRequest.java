package com.thoughtworks.springbootemployee.model;

public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;

    public EmployeeRequest(String name, Integer age, String gender, Integer salary, Integer companyId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public EmployeeRequest() {
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}

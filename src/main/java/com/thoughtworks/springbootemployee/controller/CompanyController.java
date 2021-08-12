package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> findCompanies() {
        return companyService.findCompanies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @GetMapping("/{companyId}")
    public Company findCompanyById(@PathVariable Integer companyId){
        return companyService.findCompanyById(companyId);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Integer companyId, @RequestBody Company companyToBeUpdated) {
        return companyService.updateCompany(companyId, companyToBeUpdated);
    }
}

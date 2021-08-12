package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findCompanies() {
        return companyRepository.findAll();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company findCompanyById(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public Company updateCompany(Integer companyId, Company companyToBeUpdated) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new CompanyNotFoundException("Company ID is not found");
        }
        return updateCompanyInfo(company, companyToBeUpdated);
    }

    private Company updateCompanyInfo(Company company, Company companyToBeUpdated) {
        if (companyToBeUpdated.getCompanyName() != null) {
            company.setCompanyName(companyToBeUpdated.getCompanyName());
        }

        companyRepository.save(company);
        return company;
    }

}

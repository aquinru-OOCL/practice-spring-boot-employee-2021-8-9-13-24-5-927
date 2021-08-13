package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
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
        return companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
    }

    public Company updateCompany(Integer companyId, Company companyToBeUpdated) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new CompanyNotFoundException("Company not found. Cannot update non-existent company.");
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

    public void deleteCompany(Integer employeeId) {
        Company company = companyRepository.findById(employeeId).orElse(null);
        if (company == null) {
            throw new EmployeeNotFoundException("Company not found. Cannot delete non-existent company.");
        }
        companyRepository.delete(company);
    }

    public List<Employee> getCompanyEmployees(Integer companyId) {
        return companyRepository.findById(companyId).map(Company::getEmployees).orElse(null);
    }

    public List<Company> findCompaniesByPagination(Integer pageIndex, Integer pageSize){
        return companyRepository.findAll(PageRequest.of(pageIndex, pageSize)).getContent();
    }
}

package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all() throws Exception {
        // Given
        Company company = new Company("OOCL");
        companyRepository.save(company);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value(company.getCompanyName()));
    }

    @Test
    public void should_add_company_when_call_add_company() throws Exception {
        // Given
        String stringAsJson = "{\n" +
                "    \"companyName\" : \"OOCL\",\n" +
                "    \"employees\" : [\n" +
                "        {\n" +
                "            \"name\" : \"russ\",\n" +
                "            \"age\" : 22,\n" +
                "            \"gender\" : \"male\",\n" +
                "            \"salary\" : 1000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employees").isNotEmpty())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("russ"))
                .andExpect(jsonPath("$.employees[0].age").value(22))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(1000));
    }
}

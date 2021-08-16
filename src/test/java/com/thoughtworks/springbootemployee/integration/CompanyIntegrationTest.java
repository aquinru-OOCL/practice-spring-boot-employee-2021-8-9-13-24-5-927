package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @BeforeEach
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

    @Test
    public void should_return_company_when_find_company_by_id_given_company_id() throws Exception {
        // Given
        Company company = new Company("OOCL");
        companyRepository.save(company);
        Integer companyId = company.getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("OOCL"));

    }

    @Test
    public void should_return_updated_company_when_update_given_company_id() throws Exception {
        // Given
        Company company = new Company("OOCL");
        companyRepository.save(company);
        Integer companyId = company.getId();

                String stringAsJson = "{\n" +
                "    \"companyName\" : \"updatedOOCL\"\n" +
                "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/" + companyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("updatedOOCL"));
    }

    @Test
    public void should_delete_company_when_delete_given_company_id() throws Exception {
        // Given
        Company company = new Company("OOCL");
        companyRepository.save(company);
        Integer companyId = company.getId();

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/" + companyId))
                .andExpect(status().isOk());

        // Then
        assertFalse(employeeRepository.findById(companyId).isPresent());
    }

    @Test
    public void should_return_exception_message_when_find_company_by_id_given_non_existent_company() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company not found"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andReturn();
    }

    @Test
    public void should_return_exception_message_when_update_given_non_existent_company() throws Exception {
        // Given
        String updatedCompany = "{\n" +
                "    \"companyName\" : \"updatedOOCL\"\n" +
                "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/" + 99)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCompany))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company not found. Cannot update non-existent company."))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }

    @Test
    public void should_return_exception_message_when_delete_given_non_existent_employee() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/" + 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company not found. Cannot delete non-existent company."))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }
}

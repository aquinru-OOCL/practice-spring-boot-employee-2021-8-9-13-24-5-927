package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
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
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_all_employees_when_call_find_employees() throws Exception {
        // Given
        final Employee employee = new Employee(1, "russel", 22, "male", 5000);
        employeeRepository.save(employee);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("russel"))
                .andExpect(jsonPath("$[0].age").value(22))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(5000));
    }

    @Test
    void should_add_employee_when_call_add_employee() throws Exception {
        // Given
        String employee = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"barbielot\",\n" +
                "    \"age\": 13,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 1000\n" +
                "}";

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(jsonPath("$.name").value("barbielot"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value("1000"));
    }

    @Test
    public void should_return_specific_employee_when_get_by_id_given_employee_id() throws Exception {
        // Given
        final Employee employee = new Employee(2, "marimar", 20, "female", 1122);
        employeeRepository.save(employee);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("marimar"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("female"))
                .andExpect(jsonPath("$[0].salary").value(1122));
    }

    @Test
    public void should_update_employee_when_update_given_employee() throws Exception {
        // Given
        Employee employee = new Employee(1, "russel", 22, "male", 5000);
        Employee createdEmployee = employeeRepository.save(employee);
        Integer employeeId = createdEmployee.getId();
        String updatedEmployee = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"russUpdated\",\n" +
                "    \"age\" : 23,\n" +
                "    \"gender\" : \"female\",\n" +
                "    \"salary\" : 7000\n" +
                "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("russUpdated"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(7000));
    }

    @Test
    public void should_delete_employee_when_delete_given_employee_id() throws Exception {
        // Given
        Employee employee = new Employee(2, "janley", 18, "male", 80000);
        Employee createdEmployee = employeeRepository.save(employee);
        Integer employeeId = createdEmployee.getId();

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/" + employeeId))
                .andExpect(status().isOk());

        // Then
        assertFalse(employeeRepository.findById(employee.getId()).isPresent());
    }
}

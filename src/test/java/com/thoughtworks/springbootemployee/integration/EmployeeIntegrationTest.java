package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

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
        //given
        String employee = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"barbielot\",\n" +
                "    \"age\": 13,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 1000\n" +
                "}";

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
        .andExpect(jsonPath("$.name").value("barbielot"))
        .andExpect(jsonPath("$.gender").value("male"))
        .andExpect(jsonPath("$.salary").value("1000"));
    }


}

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
        final Employee employee = new Employee("russel", 22, "male", 5000);
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
        final Employee employee = new Employee("marimar", 20, "female", 1122);
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
        Employee employee = new Employee("russel", 22, "male", 5000);
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
        Employee employee = new Employee("janley", 18, "male", 80000);
        Employee createdEmployee = employeeRepository.save(employee);
        Integer employeeId = createdEmployee.getId();

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/" + employeeId))
                .andExpect(status().isOk());

        // Then
        assertFalse(employeeRepository.findById(employee.getId()).isPresent());
    }

    @Test
    public void should_return_specific_employee_when_get_by_gender_given_employee_gender() throws Exception {
        // Given
        Employee employee1 = new Employee( "russ", 22, "male", 5000);
        Employee employee2 = new Employee("janley", 18, "male", 80000);
        Employee employee3 = new Employee("barbie", 20, "female", 9999);

        Integer employeeId1 = employeeRepository.save(employee1).getId();
        Integer employeeId2 = employeeRepository.save(employee2).getId();
        Integer employeeId3 = employeeRepository.save(employee3).getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender=male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employeeId1))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$[1].id").value(employeeId2))
                .andExpect(jsonPath("$[1].name").value(employee2.getName()))
                .andExpect(jsonPath("$[1].age").value(employee2.getAge()))
                .andExpect(jsonPath("$[1].gender").value(employee2.getGender()))
                .andExpect(jsonPath("$[1].salary").value(employee2.getSalary()));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender=female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employeeId3))
                .andExpect(jsonPath("$[0].name").value(employee3.getName()))
                .andExpect(jsonPath("$[0].age").value(employee3.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee3.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee3.getSalary()));
    }

    @Test
    public void should_return_2_employee_when_page_query_given_page_size_2() throws Exception {
        // Given
        final Employee employee1 = new Employee("russ", 22, "male", 5000);
        Employee employee2 = new Employee("janley", 18, "male", 80000);
        Employee employee3 = new Employee("barbie", 20, "female", 9999);

        Integer employeeId1 = employeeRepository.save(employee1).getId();
        Integer employeeId2 = employeeRepository.save(employee2).getId();
        employeeRepository.save(employee3);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employeeId1))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$[1].id").value(employeeId2))
                .andExpect(jsonPath("$[1].name").value(employee2.getName()))
                .andExpect(jsonPath("$[1].age").value(employee2.getAge()))
                .andExpect(jsonPath("$[1].gender").value(employee2.getGender()))
                .andExpect(jsonPath("$[1].salary").value(employee2.getSalary()));

    }

    @Test
    public void should_return_exception_message_when_find_employee_by_id_given_non_existent_employee() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andReturn();
    }

    @Test
    public void should_return_exception_message_when_update_given_non_existent_employee() throws Exception {
        // Given
        Employee employee = new Employee("russel", 22, "male", 5000);
        Employee createdEmployee = employeeRepository.save(employee);
        String updatedEmployee = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"russUpdated\",\n" +
                "    \"age\" : 23,\n" +
                "    \"gender\" : \"female\",\n" +
                "    \"salary\" : 7000\n" +
                "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + 99)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployee))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found. Cannot update non-existent employee."))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }

}

package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.RetiringEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RetiringEmployeeRepository retiringEmployeeRepository;

    @Test
    void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        // Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "russel", 22, "male", 1000));
        employees.add(new Employee(2, "janley", 18, "male", 50000));
        employees.add(new Employee(3, "barbie", 20, "female", 2000));
        given(retiringEmployeeRepository.findEmployees()).willReturn(employees);

        // When
        List<Employee> actualEmployees = employeeService.findEmployees();

        // Then
        assertEquals(employees, actualEmployees);
        assertIterableEquals(employees, actualEmployees);

    }

    @Test
    void should_return_specific_employee_when_get_employee_given_employee_id() {
        // Given
        Employee employee = new Employee(4, "ace", 21, "male", 500);
        when(retiringEmployeeRepository.findEmployeeById(4)).thenReturn(employee);

        // When
        Optional<Employee> actualEmployee = employeeService.findEmployeeById(4);

        // Then
        assertEquals(employee, actualEmployee);

    }

    @Test
    void should_return_employees_when_get_employee_by_gender_given_employee_gender() {
        // Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "russel", 22, "male", 1000));
        employees.add(new Employee(2, "janley", 18, "male", 50000));
        employees.add(new Employee(3, "barbie", 20, "female", 2000));
        when(retiringEmployeeRepository.findEmployeeByGender("male")).thenReturn(employees);

        // When
        List<Employee> actualMaleEmployees = employeeService.findEmployeeByGender("male");

        // Then
        assertEquals(employees, actualMaleEmployees);
        assertIterableEquals(employees, actualMaleEmployees);

    }

    @Test
    void should_return_list_of_employees_when_get_by_pagination_given_5_page_size() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "russel", 22, "male", 1000));
        employees.add(new Employee(2, "janley", 18, "male", 50000));
        when(retiringEmployeeRepository.findEmployeesByPagination(1,5)).thenReturn((employees));

        // When
        List<Employee> actualEmployees = employeeService.findEmployeesByPagination(1, 5);

        // Then
        assertEquals(employees, actualEmployees);
        assertIterableEquals(employees, actualEmployees);

    }

    @Test
    public void should_return_created_employee_when_add_given_one_employee() {
        // Given
        Employee employee = new Employee(1, "russel", 22, "male", 1000);
        when(retiringEmployeeRepository.addEmployee(employee)).thenReturn(employee);

        // When
        Employee actualEmployee = employeeService.addEmployee(employee);

        // Then
        assertEquals(employee, actualEmployee);
    }

    @Test
    public void should_return_updated_employee_when_update_given_one_employee() {
        // Given
        Employee employee = new Employee(1, "russel", 22, "male", 9999);
        given(retiringEmployeeRepository.updateEmployee(1, employee)).willReturn(employee);

        // When
        Employee updatedEmployee = employeeService.updateEmployee(1, employee);

        // Then
        assertEquals(9999, updatedEmployee.getSalary());
        assertEquals(employee, updatedEmployee);
    }

    @Test
    void should_delete_specified_employee_when_delete_employee_given_an_employee_id(){
        // Given
        Integer employeeId = 1;
        Employee employee = new Employee(employeeId, "russel", 22, "male", 1000);
        retiringEmployeeRepository.addEmployee(employee);

        // Given & When
        employeeService.deleteEmployee(employeeId);

        // Then
        assertNull(employeeService.findEmployeeById(1));
    }

}

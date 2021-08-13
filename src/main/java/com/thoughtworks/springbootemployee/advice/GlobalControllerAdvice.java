package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse employeeNotFoundExceptionHandling(EmployeeNotFoundException employeeNotFoundException){
        return new ErrorResponse(employeeNotFoundException.getMessage());
    }

}

package nl.sourcelabs.workshop.testing.application.web;

import java.util.List;

import nl.sourcelabs.workshop.testing.application.database.Employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeResultList {

    private final List<Employee> employees;

}

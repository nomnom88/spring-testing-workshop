package nl.sourcelabs.workshop.testing.service;

import java.util.ArrayList;
import java.util.List;

import nl.sourcelabs.workshop.testing.database.Employee;
import nl.sourcelabs.workshop.testing.database.EmployeeRepository;
import nl.sourcelabs.workshop.testing.exception.BestEmployeeMissingException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee findTheBestEmployee() {
        return employeeRepository.findTheBestEmployee().orElseThrow(BestEmployeeMissingException::new);
    }

    public List<Employee> findByFirstNameIsLike(String firstNameLike) {
        return employeeRepository.findByFirstNameIsLike("%" + firstNameLike + "%");
    }

    public List<Employee> findEmployees() {
        final Iterable<Employee> employeeIterator = employeeRepository.findAll();
        final ArrayList<Employee> employeeList = new ArrayList<>();

        employeeIterator.forEach(employeeList::add);

        return employeeList;
    }






}

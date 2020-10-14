package nl.sourcelabs.workshop.testing.application.web;

import java.util.List;

import nl.sourcelabs.workshop.testing.application.database.Employee;

public class EmployeeResultList {

    private List<Employee> employees;

    public EmployeeResultList() {
    }

    public EmployeeResultList(final List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(final List<Employee> employees) {
        this.employees = employees;
    }
}

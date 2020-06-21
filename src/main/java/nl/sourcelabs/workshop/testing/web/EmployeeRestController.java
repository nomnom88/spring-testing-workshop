package nl.sourcelabs.workshop.testing.web;

import java.util.List;

import nl.sourcelabs.workshop.testing.EmployeeService;
import nl.sourcelabs.workshop.testing.database.Employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping(path = "/api/employees")
    public List<Employee> getAllEmployees(@RequestParam(name  = "firstNameLike", required = false) String firstNameLike,
                                        @RequestParam(name = "best", required = false, defaultValue = "false") Boolean getBestEmployee) {

        if(getBestEmployee) {
            return List.of(employeeService.findTheBestEmployee());
        }

        if(firstNameLike != null) {
            return employeeService.findByFirstNameIsLike(firstNameLike);
        }
        return employeeService.findEmployees();
    }

}

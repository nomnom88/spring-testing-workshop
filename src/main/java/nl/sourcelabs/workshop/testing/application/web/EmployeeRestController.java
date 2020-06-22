package nl.sourcelabs.workshop.testing.application.web;

import java.util.List;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping(path = "/secure")
    public String securedEndpoint() {
        return "Only authenticated users can see this message";
    }

    @GetMapping(path = "/employees")
    public EmployeeResultList getAllEmployees(@RequestParam(name  = "firstNameLike", required = false) String firstNameLike,
                                        @RequestParam(name = "best", required = false, defaultValue = "false") Boolean getBestEmployee) {

        return new EmployeeResultList(getEmployeesAsList(firstNameLike, getBestEmployee));
    }

    private List<Employee> getEmployeesAsList(final String firstNameLike, final Boolean getBestEmployee) {
        if(getBestEmployee) {
            return List.of(employeeService.findTheBestEmployee());
        }

        if(firstNameLike != null) {
            return employeeService.findByFirstNameIsLike(firstNameLike);
        }

        return employeeService.findEmployees();
    }



}

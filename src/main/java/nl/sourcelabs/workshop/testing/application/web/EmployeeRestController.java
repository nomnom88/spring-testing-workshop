package nl.sourcelabs.workshop.testing.application.web;

import java.util.List;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    public EmployeeRestController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/secure")
    public String securedEndpoint() {
        return "Only authenticated users can see this message";
    }

    @GetMapping(path = "/employees")
    public EmployeeResultList getAllEmployees(@RequestParam(required = false) String firstNameLike,
                                        @RequestParam(required = false, defaultValue = "false") Boolean best) {
        return new EmployeeResultList(getEmployeesAsList(firstNameLike, best));
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

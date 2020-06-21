package nl.sourcelabs.workshop.testing.application.web;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class EmployeeRestControllerITTest {

    private static final String API_ENDPOINT = "/api/employees";

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void given_employees_when_allEmployeesRequested_then_allEmployeesReturned() throws Exception {

        final Employee e1 = new Employee();
        final String firstName = "Duncan";
        e1.setFirstName(firstName);
        final String lastName = "Campbell";
        e1.setLastName(lastName);
        final UUID id = randomUUID();
        e1.setId(id);

        final List<Employee> employees = List.of(e1, e1);
        when(employeeService.findEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees[0].id").value(id.toString()))
                .andExpect(jsonPath("$.employees[0].firstName").value(firstName))
                .andExpect(jsonPath("$.employees[0].lastName").value(lastName));

    }

}
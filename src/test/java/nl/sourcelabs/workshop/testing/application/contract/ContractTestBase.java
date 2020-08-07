package nl.sourcelabs.workshop.testing.application.contract;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;

import java.util.List;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractTestBase {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        final Employee employee1 = new Employee();
        employee1.setFirstName("Hardcore");
        employee1.setLastName("Henry");
        employee1.setId(randomUUID());

        final Employee employee2 = new Employee();
        employee2.setFirstName("Duncan");
        employee2.setLastName("Campbell");
        employee2.setId(randomUUID());

        final Employee employee3 = new Employee();
        employee3.setFirstName("Vladimir");
        employee3.setLastName("Putin");
        employee3.setId(randomUUID());

        when(employeeService.findEmployees()).thenReturn(List.of(employee1, employee2, employee3));

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

    }


}

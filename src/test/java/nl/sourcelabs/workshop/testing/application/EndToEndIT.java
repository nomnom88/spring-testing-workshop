package nl.sourcelabs.workshop.testing.application;

import static io.restassured.RestAssured.get;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.equalTo;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.database.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndIT {

    private Employee employee;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before() {
        RestAssured.port = serverPort;

        employee = new Employee();
        employee.setFirstName("Duncan");
        employee.setLastName("Campbell");
        employee.setId(randomUUID());
        employeeRepository.save(employee);
    }

    @Test
    public void given_employeesInDatabase_when_employeesRequested_then_employeesReturned() {
        get("/api/employees").peek().then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
        .body("employees[0].id", equalTo(employee.getId().toString()))
        .body("employees[0].firstName", equalTo(employee.getFirstName()))
        .body("employees[0].lastName", equalTo(employee.getLastName()));

    }

}

package nl.sourcelabs.workshop.testing.application.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.UUID;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeRestControllerRealHttpCallIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("GIVEN a valid username and password combination WHEN requesting with user THEN expect text message returned")
    void authTest() {

        final Employee employee = new Employee();
        employee.setFirstName("Duncan");
        employee.setLastName("Campbell");
        employee.setId(UUID.randomUUID());

        Mockito.when(employeeService.findEmployees()).thenReturn(Collections.singletonList(employee));

        final ResponseEntity<EmployeeResultList> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/employees", EmployeeResultList.class);

        Mockito.verify(employeeService).findEmployees();

        assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);

        final EmployeeResultList resultList = responseEntity.getBody();

        assertThat(resultList).isNotNull();
        assertThat(resultList.getEmployees()).hasSize(1);
        assertThat(resultList.getEmployees().get(0)).isEqualToComparingFieldByField(employee);
    }


}

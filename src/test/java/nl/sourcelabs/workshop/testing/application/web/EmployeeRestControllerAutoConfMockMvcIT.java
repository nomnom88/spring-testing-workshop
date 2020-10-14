package nl.sourcelabs.workshop.testing.application.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.UUID;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)  <- This setting would be redundant
@AutoConfigureMockMvc
public class EmployeeRestControllerAutoConfMockMvcIT {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void autoConfigureMockMvcTest() throws Exception {

        final Employee employee = new Employee();
        employee.setFirstName("Duncan");
        employee.setLastName("Campbell");
        employee.setId(UUID.randomUUID());

        Mockito.when(employeeService.findEmployees())
                .thenReturn(Collections.singletonList(employee));

        final String jsonEmployeeList = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final EmployeeResultList employeeResultList = objectMapper.readValue(jsonEmployeeList, EmployeeResultList.class);

        assertThat(employeeResultList).isNotNull();
        assertThat(employeeResultList.getEmployees()).hasSize(1);
        assertThat(employeeResultList.getEmployees()
                .get(0)).isEqualToComparingFieldByField(employee);
    }

}

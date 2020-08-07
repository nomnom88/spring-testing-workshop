package nl.sourcelabs.workshop.testing.application.web;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.List;

import nl.sourcelabs.workshop.testing.application.database.Employee;
import nl.sourcelabs.workshop.testing.application.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class EmployeeRestControllerIT {

    private static final String API_ENDPOINT = "/api/employees";
    private static final String SECURE_ENDPOINT = "/api/secure";

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void given_bestEmployeePresent_when_bestFlagTrue_then_onlyBestEmployeeReturned() throws Exception {

        final Employee bestEmployee = new Employee();
        bestEmployee.setFirstName("Hardcore");
        bestEmployee.setLastName("Henry");
        bestEmployee.setId(randomUUID());

        when(employeeService.findTheBestEmployee()).thenReturn(bestEmployee);

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT)
                .param("best", "true"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees[1]").doesNotExist())
                .andExpect(jsonPath("$.employees[0].id").value(bestEmployee.getId()
                        .toString()))
                .andExpect(jsonPath("$.employees[0].firstName").value(bestEmployee.getFirstName()))
                .andExpect(jsonPath("$.employees[0].lastName").value(bestEmployee.getLastName()));

    }

    @Test
    void given_employees_when_allEmployeesRequested_then_allEmployeesReturned() throws Exception {

        final Employee employeeOne = new Employee();
        employeeOne.setFirstName("Duncan");
        employeeOne.setLastName("Campbell");
        employeeOne.setId(randomUUID());

        final Employee employeeTwo = new Employee();
        employeeTwo.setFirstName("Hardcore");
        employeeTwo.setLastName("Henry");
        employeeTwo.setId(randomUUID());

        final Employee employeeThree = new Employee();
        employeeThree.setFirstName("Willy");
        employeeThree.setLastName("Wonka");
        employeeThree.setId(randomUUID());

        when(employeeService.findEmployees()).thenReturn(List.of(employeeOne, employeeTwo, employeeThree));

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees[0].id").value(employeeOne.getId()
                        .toString()))
                .andExpect(jsonPath("$.employees[0].firstName").value(employeeOne.getFirstName()))
                .andExpect(jsonPath("$.employees[0].lastName").value(employeeOne.getLastName()))
                .andExpect(jsonPath("$.employees[1].id").value(employeeTwo.getId()
                        .toString()))
                .andExpect(jsonPath("$.employees[1].firstName").value(employeeTwo.getFirstName()))
                .andExpect(jsonPath("$.employees[1].lastName").value(employeeTwo.getLastName()))
                .andExpect(jsonPath("$.employees[2].id").value(employeeThree.getId()
                        .toString()))
                .andExpect(jsonPath("$.employees[2].firstName").value(employeeThree.getFirstName()))
                .andExpect(jsonPath("$.employees[2].lastName").value(employeeThree.getLastName()));

    }

    @Test
    void given_findByFirstNameLike_when_partOfFirstNameSubmitted_then_onlyMatchingNamesReturned() throws Exception {

        final Employee employeeOne = new Employee();
        employeeOne.setFirstName("match1");
        employeeOne.setLastName("lastName1");
        employeeOne.setId(randomUUID());

        final Employee employeeTwo = new Employee();
        employeeTwo.setFirstName("Another-match2");
        employeeTwo.setLastName("lastName1");
        employeeTwo.setId(randomUUID());

        final String firstNameLikeParam = "at";
        when(employeeService.findByFirstNameIsLike(firstNameLikeParam)).thenReturn(List.of(employeeOne, employeeTwo));

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT)
                .param("firstNameLike", firstNameLikeParam))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees[2]").doesNotExist())
                .andExpect(jsonPath("$.employees[0].id").value(employeeOne.getId()
                        .toString()))
                .andExpect(jsonPath("$.employees[0].firstName").value(employeeOne.getFirstName()))
                .andExpect(jsonPath("$.employees[0].lastName").value(employeeOne.getLastName()))
                .andExpect(jsonPath("$.employees[1].id").value(employeeTwo.getId()
                        .toString()))
                .andExpect(jsonPath("$.employees[1].firstName").value(employeeTwo.getFirstName()))
                .andExpect(jsonPath("$.employees[1].lastName").value(employeeTwo.getLastName()));

    }

    @Test
    void given_authenticatedUser_when_credentialsSuppliedOnSecureEndpoint_then_messageReturned() throws Exception {

        final String encodedCredentials = Base64.getEncoder()
                .encodeToString("user:password".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.get(SECURE_ENDPOINT).header(HttpHeaders.AUTHORIZATION, "Basic "+encodedCredentials))
                .andExpect(status().isOk())
                .andExpect(content().string("Only authenticated users can see this message"));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void given_authenticatedUserInjectedAsMockUser_when_credentialsSuppliedOnSecureEndpoint_then_messageReturned() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(SECURE_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string("Only authenticated users can see this message"));

    }

    @Test
    void given_nonAuthenticatedUser_when_credentialsMissingOnSecureEndpoint_then_errorCodeReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(SECURE_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void given_authenticatedUser_when_wrongCredentialsSuppliedOnSecureEndpoint_then_errorCodeReturned() throws Exception {

        final String encodedCredentials = Base64.getEncoder()
                .encodeToString("user:not-my-password".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.get(SECURE_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Basic "+encodedCredentials))
                .andExpect(status().isUnauthorized());

    }

}
package nl.sourcelabs.workshop.testing.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = Application.class)
public class EmployeeRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository sut;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(sut).isNotNull();
    }

    @Test
    void findTheBestEmployee() {

        final String expectedFirstName = "Duncan";
        final String expectedLastName = "Campbell";
        final UUID expectedId = UUID.randomUUID();

        makeAndPersistEmployee(expectedFirstName, expectedLastName, expectedId);

        final Optional<Employee> result = sut.findTheBestEmployee();

        assertThat(result).isNotNull();
        assertThat(result).isPresent();

        final Employee employeeResult = result.orElseThrow(IllegalStateException::new);
        assertThat(employeeResult.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(employeeResult.getLastName()).isEqualTo(expectedLastName);
        assertThat(employeeResult.getId()).isEqualTo(expectedId);

    }

    private Employee makeAndPersistEmployee(final String expectedFirstName, final String expectedLastName, final UUID expectedId) {
        final Employee employee = new Employee();
        employee.setFirstName(expectedFirstName);
        employee.setLastName(expectedLastName);
        employee.setId(expectedId);
        entityManager.persist(employee);
        return employee;
    }

}

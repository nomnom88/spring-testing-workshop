package nl.sourcelabs.workshop.testing.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
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

        assertThat(employeeResult).isEqualToComparingFieldByField(employeeResult); //does the same as the previous 3 lines
    }

    @Test
    void findByFirstNameIsLike() {
        final String expectedFirstName = "Bruce";
        final String expectedLastName = "Wayne";
        final UUID expectedId = UUID.randomUUID();

        final Employee persistedEmployee = makeAndPersistEmployee(expectedFirstName, expectedLastName, expectedId);

        final List<Employee> firstEmployeeMatchResult = sut.findByFirstNameIsLike("%ruc%");
        assertThat(firstEmployeeMatchResult).hasSize(1);
        final Employee firstEmployeeMatch = firstEmployeeMatchResult.get(0);
        assertThat(firstEmployeeMatch).isEqualTo(persistedEmployee);

        final List<Employee> secondEmployeeMatchResult = sut.findByFirstNameIsLike("Bruc%");
        assertThat(secondEmployeeMatchResult).hasSize(1);
        final Employee secondEmployeeMatch = secondEmployeeMatchResult.get(0);
        assertThat(secondEmployeeMatch).isEqualTo(persistedEmployee);

        final List<Employee> thirdEmployeeMatchResult = sut.findByFirstNameIsLike("%ruce");
        assertThat(thirdEmployeeMatchResult).hasSize(1);
        final Employee thirdEmployeeMatch = thirdEmployeeMatchResult.get(0);
        assertThat(thirdEmployeeMatch).isEqualTo(persistedEmployee);

    }

    @Test
    void isPagingAndSortingRepository() {
        assertThat(sut).isInstanceOf(PagingAndSortingRepository.class);
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

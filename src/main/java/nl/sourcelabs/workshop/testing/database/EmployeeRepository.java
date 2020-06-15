package nl.sourcelabs.workshop.testing.database;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, UUID> {

    @Query(value = "select e from Employee e where e.firstName = 'Duncan' and e.lastName = 'Campbell'",
    nativeQuery = false) //set this to true to run native SQL, keep dialects in mind
    Optional<Employee> findTheBestEmployee();

    Optional<Employee> findByFirstNameIsLike(String firstNameLike);
}

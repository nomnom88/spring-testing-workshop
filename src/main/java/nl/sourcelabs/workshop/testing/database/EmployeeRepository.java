package nl.sourcelabs.workshop.testing.database;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, UUID> {

}

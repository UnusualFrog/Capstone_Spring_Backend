package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Employee;
import org.example.capstone.pojos.Home;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Employee} entities.
 * <p>
 * Provides standard CRUD operations and custom methods for login and identity checks.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    /**
     * Retrieves all employee records by a specific employee ID.
     * <p>
     * Note: This may be redundant since {@code findById(Long)} already exists via {@link CrudRepository}.
     * Also, returning an iterable for a single ID is uncommon—use with caution.
     *
     * @param employeeId The ID of the employee.
     * @return An iterable list containing the {@link Employee} record.
     */
    Iterable<Home> getAllEmployeesById(Long employeeId);

    /**
     * Checks whether an employee exists with the given username.
     *
     * @param username The username to check.
     * @return True if an employee with that username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Finds an employee by their username (used for authentication).
     *
     * @param username The username of the employee.
     * @return The matching {@link Employee}, or null if not found.
     */
    Employee findByUsername(String username);
}

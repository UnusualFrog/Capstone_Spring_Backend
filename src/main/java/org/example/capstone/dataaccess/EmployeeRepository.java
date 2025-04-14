package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Employee;
import org.example.capstone.pojos.Home;
import org.springframework.data.repository.CrudRepository;

/**
 * Home Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    /**
     * Get all autos for a user
     * @param employeeId
     * @return
     */
    Iterable<Home> getAllEmployeesById(Long employeeId);
}

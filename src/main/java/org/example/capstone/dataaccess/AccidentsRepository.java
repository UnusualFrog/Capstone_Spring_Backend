package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Accident;
import org.example.capstone.pojos.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Accidents Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface AccidentsRepository extends CrudRepository<Accident, Long> {

    Iterable<Accident> getAllAccidentsByCustomer(Customer customer);
}

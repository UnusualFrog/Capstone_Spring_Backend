package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Accident;
import org.example.capstone.pojos.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for performing CRUD operations on {@link Accident} entities.
 * <p>
 * Extends {@link CrudRepository} to inherit basic CRUD methods.
 */
public interface AccidentsRepository extends CrudRepository<Accident, Long> {

    /**
     * Retrieves all accidents associated with a specific customer.
     *
     * @param customer The customer whose accidents are to be retrieved.
     * @return An iterable collection of {@link Accident} entities.
     */
    Iterable<Accident> getAllAccidentsByCustomer(Customer customer);
}

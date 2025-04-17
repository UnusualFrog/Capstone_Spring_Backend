package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Auto;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Auto} entities.
 * <p>
 * Provides standard CRUD operations and a custom query method for retrieving autos by customer.
 */
public interface AutoRepository extends CrudRepository<Auto, Long> {

    /**
     * Retrieves all vehicles (autos) registered to a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return An iterable list of {@link Auto} entities linked to the given customer.
     */
    Iterable<Auto> getAllByCustomerId(Long customerId);
}

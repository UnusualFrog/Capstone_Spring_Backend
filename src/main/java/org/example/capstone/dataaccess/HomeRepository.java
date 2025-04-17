package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Customer;
import org.example.capstone.pojos.Home;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Home} entities.
 * <p>
 * Provides standard CRUD operations and custom methods for retrieving homes by customer or ID.
 */
public interface HomeRepository extends CrudRepository<Home, Long> {

    /**
     * Retrieves all homes with a specific home ID.
     * <p>
     * Note: This method may be redundant with {@code findById(Long)} from {@link CrudRepository},
     * unless intentionally returning multiple records.
     *
     * @param userId The home ID.
     * @return An iterable list of matching {@link Home} entries.
     */
    Iterable<Home> getAllHomesById(Long userId);

    /**
     * Retrieves all homes associated with a given customer.
     *
     * @param customer The {@link Customer} whose homes are to be fetched.
     * @return An iterable list of {@link Home} entities for that customer.
     */
    Iterable<Home> getAllHomesByCustomer(Customer customer);
}

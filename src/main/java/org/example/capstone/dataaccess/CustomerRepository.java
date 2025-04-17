package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Customer} entities.
 * <p>
 * Provides CRUD operations and custom query methods for user authentication and lookup.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    /**
     * Retrieves a customer by their unique ID.
     * <p>
     * Note: This is likely redundant with {@code findById(Long)} from {@link CrudRepository},
     * unless an {@code Integer}-typed ID is explicitly required elsewhere.
     *
     * @param id The customer's ID.
     * @return The {@link Customer} object, or null if not found.
     */
    Customer getCustomerById(Integer id);

    /**
     * Checks whether a customer exists with the given username.
     *
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Finds a customer by their username (used during login).
     *
     * @param username The customer's username.
     * @return The {@link Customer} with the given username, or null if not found.
     */
    Customer findByUsername(String username);

    /**
     * Retrieves all customers with a specific first and last name.
     *
     * @param firstName The first name.
     * @param lastName  The last name.
     * @return An iterable list of matching {@link Customer} records.
     */
    Iterable<Customer> getAllCustomersByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Retrieves all customers with the specified email address.
     *
     * @param email The email address to search for.
     * @return An iterable list of matching {@link Customer} records.
     */
    Iterable<Customer> getAllCustomersByEmail(String email);

}

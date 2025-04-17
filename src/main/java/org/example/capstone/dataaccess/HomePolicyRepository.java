package org.example.capstone.dataaccess;

import org.example.capstone.pojos.HomePolicy;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link HomePolicy} entities.
 * <p>
 * Provides CRUD operations and custom queries for retrieving home insurance policies by customer and status.
 */
public interface HomePolicyRepository extends CrudRepository<HomePolicy, Long> {

    /**
     * Retrieves home policies by a given policy ID.
     * <p>
     * Note: This may be redundant with {@code findById(Long)} from {@link CrudRepository},
     * unless the method is intentionally returning multiple results.
     *
     * @param homePolicyId The ID of the home policy.
     * @return An iterable list of matching {@link HomePolicy} records.
     */
    Iterable<HomePolicy> getHomePolicyById(Long homePolicyId);

    /**
     * Retrieves all home policies associated with a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return An iterable list of {@link HomePolicy} entities.
     */
    Iterable<HomePolicy> getAllByCustId(Long customerId);

    /**
     * Retrieves all active or inactive home policies for a specific customer.
     *
     * @param customerID The ID of the customer.
     * @param active     Whether to return active (true) or inactive (false) policies.
     * @return An iterable list of {@link HomePolicy} entities filtered by active status.
     */
    Iterable<HomePolicy> getAllActiveByCustIdAndActive(Long customerID, boolean active);
}

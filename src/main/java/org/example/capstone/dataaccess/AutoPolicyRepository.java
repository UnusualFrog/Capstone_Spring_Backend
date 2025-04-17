package org.example.capstone.dataaccess;

import org.example.capstone.pojos.AutoPolicy;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link AutoPolicy} entities.
 * <p>
 * Provides standard CRUD operations and custom query methods for retrieving auto policies
 * by customer ID and active status.
 */
public interface AutoPolicyRepository extends CrudRepository<AutoPolicy, Long> {

    /**
     * Retrieves all auto policies with the specified policy ID.
     * <p>
     * Note: This may be redundant with {@link CrudRepository#findById(Object)} unless returning multiple results is intentional.
     *
     * @param autoPolicyID The policy ID to filter by.
     * @return An iterable of matching {@link AutoPolicy} entries.
     */
    Iterable<AutoPolicy> getAutoPoliciesById(Long autoPolicyID);

    /**
     * Retrieves all auto policies associated with a given customer ID.
     *
     * @param customerId The ID of the customer.
     * @return An iterable of {@link AutoPolicy} entities for the customer.
     */
    Iterable<AutoPolicy> getAllByCustId(Long customerId);

    /**
     * Retrieves all active or inactive auto policies for a specific customer.
     *
     * @param customerID The ID of the customer.
     * @param active     Whether to retrieve active (true) or inactive (false) policies.
     * @return An iterable of {@link AutoPolicy} entities filtered by active status.
     */
    Iterable<AutoPolicy> getAllActiveByCustIdAndActive(Long customerID, boolean active);
}

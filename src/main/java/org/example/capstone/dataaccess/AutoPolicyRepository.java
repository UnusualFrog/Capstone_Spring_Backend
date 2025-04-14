package org.example.capstone.dataaccess;

import org.example.capstone.pojos.AutoPolicy;
import org.springframework.data.repository.CrudRepository;

/**
 * Auto Policy Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface AutoPolicyRepository extends CrudRepository<AutoPolicy, Long> {

    /**
     * Get all quotes for an id
     * @param autoPolicyID
     * @return
     */
    Iterable<AutoPolicy> getAutoPoliciesById(Long autoPolicyID);
    Iterable<AutoPolicy> getAllByCustId(Long customerId);
}

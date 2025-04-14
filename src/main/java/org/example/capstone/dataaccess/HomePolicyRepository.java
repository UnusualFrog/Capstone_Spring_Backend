package org.example.capstone.dataaccess;

import org.example.capstone.pojos.HomePolicy;
import org.example.capstone.pojos.HomeQuote;
import org.springframework.data.repository.CrudRepository;

/**
 * Home Policy Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface HomePolicyRepository extends CrudRepository<HomePolicy, Long> {

    /**
     * Get all quotes for an id
     * @param homePolicyId
     * @return
     */
    Iterable<HomePolicy> getHomePolicyById(Long homePolicyId);
    Iterable<HomePolicy> getHomePolicyByCustId(Long customerId);
}

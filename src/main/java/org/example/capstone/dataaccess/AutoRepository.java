package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Auto;
import org.example.capstone.pojos.OLD_Auto;
import org.springframework.data.repository.CrudRepository;

/**
 * Auto Repository interface that will be used by String to create a bean that handles all the CRUD operations
 */
public interface AutoRepository extends CrudRepository<Auto, Long> {

    /**
     * Get all auto for a user
     * @param customerId
     * @return
     */
    Iterable<Auto> getAllByCustomerId(Long customerId);
}

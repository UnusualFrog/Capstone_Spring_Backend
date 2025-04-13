package org.example.capstone.dataaccess;

import org.example.capstone.pojos.OLD_Home;
import org.springframework.data.repository.CrudRepository;

/**
 * Auto Repository interface that will be used by String to create a bean that handles all the CRUD operations
 */
public interface HomeRepository extends CrudRepository<OLD_Home, Long> {

    /**
     * Get all autos for a user
     * @param userId
     * @return
     */
    Iterable<OLD_Home> getAllByUserId(Long userId);
}

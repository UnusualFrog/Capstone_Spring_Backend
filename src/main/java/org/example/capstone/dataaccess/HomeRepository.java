package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Customer;
import org.example.capstone.pojos.Home;
import org.springframework.data.repository.CrudRepository;

/**
 * Home Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface HomeRepository extends CrudRepository<Home, Long> {

    /**
     * Get all autos for a user
     * @param userId
     * @return
     */
    Iterable<Home> getAllHomesById(Long userId);
    Iterable<Home> getAllHomesByCustomer(Customer customer);
}

package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * Address Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface AddressRepository extends CrudRepository<Address, Long> {

    Iterable<Address> getAllAddressessById(Long id);
}

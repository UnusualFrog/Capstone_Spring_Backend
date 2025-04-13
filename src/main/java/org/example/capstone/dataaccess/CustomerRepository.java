package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Customer Repository interface that will be used by String to create a bean that handles all the CRUD operations
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer getCustomerById(Integer id);

    //This is where you write code needed beyond the basics

}

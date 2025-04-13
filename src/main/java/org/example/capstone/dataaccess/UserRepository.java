package org.example.capstone.dataaccess;

import org.example.capstone.pojos.OLD_User;
import org.springframework.data.repository.CrudRepository;

/**
 * User Repository interface that will be used by String to create a bean that handles all the CRUD operations
 */
public interface UserRepository extends CrudRepository<OLD_User, Long> {
    OLD_User getUserById(Integer id);

    //This is where you write code needed beyond the basics

}

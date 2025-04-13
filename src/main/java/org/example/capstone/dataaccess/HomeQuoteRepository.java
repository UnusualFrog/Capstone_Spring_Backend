package org.example.capstone.dataaccess;

import org.example.capstone.pojos.AutoQuote;
import org.example.capstone.pojos.HomeQuote;
import org.springframework.data.repository.CrudRepository;

/**
 * Home Quote Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface HomeQuoteRepository extends CrudRepository<HomeQuote, Long> {

    /**
     * Get all quotes for an id
     * @param homeQuoteID
     * @return
     */
    Iterable<HomeQuote> getHomeQuotesById(Long homeQuoteID);
}

package org.example.capstone.dataaccess;

import org.example.capstone.pojos.AutoQuote;
import org.example.capstone.pojos.HomeQuote;
import org.springframework.data.repository.CrudRepository;

/**
 * Auto Quote Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface AutoQuoteRepository extends CrudRepository<AutoQuote, Long> {

    /**
     * Get all quotes for an id
     * @param autoQuoteID
     * @return
     */
    Iterable<AutoQuote> getAutoQuotesById(Long autoQuoteID);
//    Iterable<AutoQuote> getAllAutoQuotesById(Long userId);
    Iterable<AutoQuote> getAllByCustId(Long customerID);
    Iterable<AutoQuote> getAllActiveByCustIdAndActive(Long customerID, boolean active);
}

package org.example.capstone.dataaccess;

import org.example.capstone.pojos.HomeQuote;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link HomeQuote} entities.
 * <p>
 * Provides CRUD operations and custom queries for retrieving home insurance quotes
 * by customer ID and active status.
 */
public interface HomeQuoteRepository extends CrudRepository<HomeQuote, Long> {

    /**
     * Retrieves home quotes by a specific quote ID.
     * Note: This method may be redundant with {@link CrudRepository#findById(Object)},
     * unless you're returning a list of quotes by design.
     * @param homeQuoteID The ID of the home quote.
     * @return An iterable list of matching {@link HomeQuote} records.
     */
    Iterable<HomeQuote> getHomeQuotesById(Long homeQuoteID);

    /**
     * Retrieves all home quotes for a given customer.
     * @param customerID The ID of the customer.
     * @return An iterable list of {@link HomeQuote} entities associated with the customer.
     */
    Iterable<HomeQuote> getAllByCustId(Long customerID);

    /**
     * Retrieves all active or inactive home quotes for a given customer.
     * @param customerID The ID of the customer.
     * @param active     Whether to retrieve active (true) or inactive (false) quotes.
     * @return An iterable list of {@link HomeQuote} entities filtered by active status.
     */
    Iterable<HomeQuote> getAllActiveByCustIdAndActive(Long customerID, boolean active);
}

package org.example.capstone.dataaccess;

import org.example.capstone.pojos.AutoQuote;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link AutoQuote} entities.
 * <p>
 * Provides CRUD operations and custom queries related to auto insurance quotes.
 */
public interface AutoQuoteRepository extends CrudRepository<AutoQuote, Long> {

    /**
     * Retrieves all auto quotes with the given quote ID.
     * <p>
     * Note: This may be redundant with {@code findById(Long)} unless multiple results are expected.
     *
     * @param autoQuoteID The ID of the auto quote.
     * @return An iterable list of matching {@link AutoQuote} entities.
     */
    Iterable<AutoQuote> getAutoQuotesById(Long autoQuoteID);
//    Iterable<AutoQuote> getAllAutoQuotesById(Long userId);

    /**
     * Retrieves all auto quotes associated with a specific customer.
     *
     * @param customerID The ID of the customer.
     * @return An iterable list of {@link AutoQuote} entities.
     */
    Iterable<AutoQuote> getAllByCustId(Long customerID);

    /**
     * Retrieves all active or inactive auto quotes for a specific customer.
     *
     * @param customerID The ID of the customer.
     * @param active     Whether the quote is active (true) or inactive (false).
     * @return An iterable list of {@link AutoQuote} entities filtered by active status.
     */
    Iterable<AutoQuote> getAllActiveByCustIdAndActive(Long customerID, boolean active);
}

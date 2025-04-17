package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Address} entities.
 * <p>
 * Provides basic CRUD operations and custom queries using Spring Data.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {

    /**
     * Retrieves all addresses by their unique ID.
     * <p>
     * Note: This method is likely redundant, as {@code findById(Long id)} is already provided by {@link CrudRepository}.
     *
     * @param id The ID of the address.
     * @return An iterable collection containing the address, if found.
     */
    Iterable<Address> getAllAddressessById(Long id);

    /**
     * Checks if an address exists by matching all address components.
     *
     * @param unit        The unit number of the address.
     * @param street      The street name.
     * @param city        The city.
     * @param province    The province.
     * @param postalCode  The postal code.
     * @return True if a matching address exists, otherwise false.
     */
    boolean existsByUnitAndStreetAndCityAndProvinceAndPostalCode(Integer unit, String street, String city, String province, String postalCode);

    /**
     * Retrieves an address by matching all components of the address.
     *
     * @param unit        The unit number of the address.
     * @param street      The street name.
     * @param city        The city.
     * @param province    The province.
     * @param postalCode  The postal code.
     * @return An {@link Optional} containing the matching address, if found.
     */
    Optional<Address> getAddressByUnitAndStreetAndCityAndProvinceAndPostalCode(Integer unit, String street, String city, String province, String postalCode);
}

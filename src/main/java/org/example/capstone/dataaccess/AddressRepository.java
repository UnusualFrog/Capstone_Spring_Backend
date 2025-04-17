package org.example.capstone.dataaccess;

import org.example.capstone.pojos.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Address Repository interface that will be used by Spring to create a bean that handles all the CRUD operations
 */
public interface AddressRepository extends CrudRepository<Address, Long> {

    Iterable<Address> getAllAddressessById(Long id);
    boolean existsByUnitAndStreetAndCityAndProvinceAndPostalCode(Integer unit, String street, String city, String province, String postalCode);
    Optional<Address> getAddressByUnitAndStreetAndCityAndProvinceAndPostalCode(Integer unit, String street, String city, String province, String postalCode);
}

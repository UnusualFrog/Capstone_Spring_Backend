package org.example.capstone.controllers;

import org.example.capstone.dataaccess.*;
import org.example.capstone.pojos.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The main controller for this application, handling RESTful endpoints
 * for User, Home and Auto resources. Controllers are organized by base URL
 * and provide CRUD (Create, Read, Update, Delete) operations.
 */
@Controller
@RequestMapping(path = RESTNouns.VERSION_1)
public class MainController {

    //Wire the ORM
    @Autowired private CustomerRepository customerRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private HomeRepository homeRepository;
    @Autowired private AutoRepository autoRepository;
    @Autowired private HomeQuoteRepository homeQuoteRepository;
    @Autowired private HomePolicyRepository homePolicyRepository;
    @Autowired private AutoQuoteRepository autoQuoteRepository;
    @Autowired private AutoPolicyRepository autoPolicyRepository;

    /* *
     *  Customer METHODS
     * */

    /**
     * Retrieves all customers from the database.
     *
     * @return An iterable collection of all User entities
     */
    @GetMapping(path = RESTNouns.CUSTOMER)
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param customerId The unique identifier of the user to retrieve
     * @return An Optional containing the User if found, or an empty Optional
     */
    @GetMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody Optional<Customer> getCustomer(@PathVariable("id") Long customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Creates a new customer in the database.
     *
     * @param name The name of the customer to create
     * @return The newly created User entity
     */
    @PostMapping(path = RESTNouns.CUSTOMER)
    public @ResponseBody Customer createCustomer(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Integer accidentCount,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password
            ) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAge(age);
        customer.setAccidentCount(accidentCount);
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setPassword(password);
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer from the database by their unique identifier.
     *
     * @param customerId The unique identifier of the user to delete
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody String deleteCustomer(@PathVariable("id") Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return "Customer with ID " + customerId + " deleted successfully.";
        } else {
            return "Customer with ID " + customerId + " not found.";
        }
    }

    /**
     * Updates an existing customer's information.
     *
     * @param customerId The unique identifier of the user to update
     * @param name The new name for the user
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody String updateUser(
            @PathVariable("id") Long customerId,
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Integer accidentCount,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password){
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if(customer.isPresent()){
                customer.get().setName(name);
                customer.get().setAge(age);
                customer.get().setAccidentCount(accidentCount);
                customer.get().setEmail(email);
                customer.get().setUsername(username);
                customer.get().setPassword(password);
                customerRepository.save(customer.get());
            }
            return "Customer with ID " + customerId + " updated successfully.";
        } else {
            return "Customer with ID " + customerId + " not found.";
        }
    }

    /* *
            EMPLOYEE METHODS
     * */

    /**
     * Retrieves all customers from the database.
     *
     * @return An iterable collection of all User entities
     */
    @GetMapping(path = RESTNouns.EMPLOYEE)
    public @ResponseBody Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /* *
     *  HOME METHODS
     *
     * */

    /**
     * Retrieves all homes associated with a specific customer.
     *
     * @param customerId The unique identifier of the customer whose homes are to be retrieved
     * @return An iterable collection of Home entities belonging to the specified customer
     */
    @GetMapping(path = RESTNouns.CUSTOMER +  RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody Iterable<Home> getAllHomesByUser(@PathVariable("id") Long customerId) {
        Iterable<Home> homes = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> user = customerRepository.findById(customerId);
            if(user.isPresent()){
                homes = homeRepository.getAllHomesById(customerId);
            }
        }

        //TODO handle errors

        return homes;
    }

    /**
     * Creates a new home for a specific customer.
     *
     * @param customerId The unique identifier of the user for whom the home is being created
     * @param dateBuilt The date when the home was built
     * @param homeValue The monetary value of the home
     * @return The newly created Home entity, or null if the user does not exist
     */
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody Home createHomeByCustomer(
            @PathVariable("id") Long customerId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int homeValue,
            @RequestParam Home.HeatingType heatingType,
            @RequestParam Home.Location location) {
        Home home = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                home = new Home();
                home.setHomeValue(homeValue);
                home.setDateBuilt(dateBuilt);
                home.setCustomer(customer.get());
                home.setHeatingType(heatingType);
                home.setLocation(location);
                homeRepository.save(home);
            }
        }

        return home;
    }

    /**
     * Updates an existing home associated with a specific customer.
     *
     * @param customerId The unique identifier of the user who owns the home
     * @param homeId The unique identifier of the home to be updated
     * @param dateBuilt The new date when the home was built
     * @param homeValue The new monetary value of the home
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID + RESTNouns.HOME + RESTNouns.HOME_ID)
    public @ResponseBody String updateHomeByCustomer(
            @PathVariable("customer_id") Long customerId, @PathVariable("home_id") Long homeId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int homeValue,
            @RequestParam Home.HeatingType heatingType,
            @RequestParam Home.Location location){
        if (customerRepository.existsById(customerId) && homeRepository.existsById(homeId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<Home> home = homeRepository.findById(homeId);
            if(customer.isPresent() && home.isPresent()){
                home.get().setDateBuilt(dateBuilt);
                home.get().setHomeValue(homeValue);
                home.get().setHeatingType(heatingType);
                home.get().setLocation(location);

                homeRepository.save(home.get());
                customerRepository.save(customer.get());
            }
            return "Home with ID " + homeId + " updated successfully.";
        } else {
            return "Home with ID " + homeId + " not found.";
        }
    }

    /**
     * Deletes a specific home associated with a user.
     *
     * @param customerId The unique identifier of the user who owns the home
     * @param homeId The unique identifier of the home to be deleted
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID + RESTNouns.HOME + RESTNouns.HOME_ID)
    public @ResponseBody String deleteHomeByCustomer(
            @PathVariable("customer_id") Long customerId, @PathVariable("home_id") Long homeId) {
        if (customerRepository.existsById(customerId) && homeRepository.existsById(homeId)) {
            homeRepository.deleteById(homeId);
            return "Home with ID " + homeId + " deleted successfully.";
        } else {
            return "Home with ID " + homeId + " not found.";
        }
    }

    /* *
     *  AUTO METHODS
     * */

    /**
     * Retrieves all auto objects associated with a specific user.
     *
     * @param customerId The unique identifier of the user whose auto objects are to be retrieved
     * @return An iterable collection of Auto entities belonging to the specified user,
     *         or null if the user does not exist
     */
    @GetMapping(path = RESTNouns.CUSTOMER +  RESTNouns.ID + RESTNouns.AUTO)
    public @ResponseBody Iterable<Auto> getAllAutosByUser(@PathVariable("id") Long customerId) {
        Iterable<Auto> autos = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if(customer.isPresent()){
                autos = autoRepository.getAllByCustomerId(customerId);
            }
        }
        return autos;
    }

    /**
     * Creates a new auto object for a specific user.
     *
     * @param customerId The unique identifier of the user for whom the auto is being created
     * @param dateBuilt The date when the auto was built
     * @param value The monetary value of the auto
     * @return The newly created Auto entity, or null if the user does not exist
     */
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID + RESTNouns.AUTO)
    public @ResponseBody Auto createAutoByCustomer(
            @PathVariable("id") Long customerId,
            @RequestParam LocalDate dateBuilt, @RequestParam int value) {
        Auto auto = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                auto = new Auto();
//                auto.setValue(value);
//                auto.setDateBuilt(dateBuilt);
                auto.setCustomer(customer.get());
                autoRepository.save(auto);
            }
        }

        return auto;
    }

    /**
     * Updates an existing auto object associated with a specific user.
     *
     * @param userId The unique identifier of the user who owns the auto
     * @param autoId The unique identifier of the auto to be updated
     * @param dateBuilt The new date when the auto was built
     * @param value The new monetary value of the auto
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID + RESTNouns.AUTO + RESTNouns.AUTO_ID)
    public @ResponseBody String updateAutoByCustomer(
            @PathVariable("customer_id") Long userId, @PathVariable("auto_id") Long autoId, @RequestParam LocalDate dateBuilt, @RequestParam int value){
        if (customerRepository.existsById(userId) && autoRepository.existsById(autoId)) {
            Optional<Customer> customer = customerRepository.findById(userId);
            Optional<Auto> auto = autoRepository.findById(autoId);
            if(customer.isPresent() && auto.isPresent()){
//                auto.get().setDateBuilt(dateBuilt);
//                auto.get().setValue(value);

                autoRepository.save(auto.get());
                customerRepository.save(customer.get());
            }
            return "Auto with ID " + autoId + " updated successfully.";
        } else {
            return "Auto with ID " + autoId + " not found.";
        }
    }

    /**
     * Deletes a specific auto object associated with a customer.
     *
     * @param customerId The unique identifier of the customer who owns the auto
     * @param autoId The unique identifier of the auto to be deleted
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID + RESTNouns.AUTO + RESTNouns.AUTO_ID)
    public @ResponseBody String deleteAutoByCustomer(
            @PathVariable("customer_id") Long customerId, @PathVariable("auto_id") Long autoId) {
        if (customerRepository.existsById(customerId) && autoRepository.existsById(autoId)) {
            autoRepository.deleteById(autoId);
            return "Auto with ID " + autoId + " deleted successfully.";
        } else {
            return "Auto with ID " + autoId + " not found.";
        }
    }

    /* *
     *  HOME QUOTE METHODS
     * */
    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ID)
    public @ResponseBody Optional<HomeQuote> getHomeQuoteById(@PathVariable("id") Long quoteID) {
        return homeQuoteRepository.findById(quoteID);
    }

    @PostMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.CUSTOMER_ID)
    public @ResponseBody HomeQuote createHomeQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int value) {
        HomeQuote homeQuote = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                homeQuote = new HomeQuote();
//                auto.setValue(value);
//                auto.setDateBuilt(dateBuilt);
//                auto.setCustomer(customer.get());
                homeQuoteRepository.save(homeQuote);
            }
        }

        return homeQuote;
    }

    /* *
     *  AUTO QUOTE METHODS
     * */
    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ID)
    public @ResponseBody Optional<AutoQuote> getAutoQuoteById(@PathVariable("id") Long quoteID) {
        return autoQuoteRepository.findById(quoteID);
    }

    @PostMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.CUSTOMER_ID)
    public @ResponseBody AutoQuote createAutoQuoteForCustomer(
            @PathVariable("customer_id") Long customerId,
            @RequestParam Integer customerAge,
            @RequestParam Integer vehicleAge,
            @RequestParam Integer accidents
    ) {
        AutoQuote quote = new AutoQuote();
        double factor = 1;
        if (customerAge < 25) {
            factor *= 2;
        }
        if (accidents > 1) {
            factor *= 2.5;
        } else if (accidents == 1) {
            factor *= 1.25;
        }
        if (vehicleAge > 10) {
            factor *= 2;
        } else if (vehicleAge > 5) {
            factor *= 1.5;
        }
        if (homePolicyRepository.getHomePolicyByCustId(customerId).iterator().hasNext()) {
            for (HomePolicy policy : homePolicyRepository.getHomePolicyByCustId(customerId)) {
                System.out.println();
            }
            System.out.println();
        }
        /* Calculation Info
            Base: 750
            Driver Age: <25 = 2; 1
            Accidents: >=2 = 2.5; 1 = 1.25; 1
            Vehicle Age: >10 = 2; >5 = 1.5; 1
            Discount: Active Home = 0.9
            Base * All Factors * Tax Rate
         */
//        quote.calculateX(name);

        return autoQuoteRepository.save(quote);
    }







    /* *
     *  HOME POLICY METHODS
     * */
    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ID)
    public @ResponseBody Optional<HomePolicy> getHomePolicyById(@PathVariable("id") Long quoteID) {
        return homePolicyRepository.findById(quoteID);
    }

    @PostMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER_ID)
    public @ResponseBody HomePolicy createHomePolicyByCustomer(
            @PathVariable("customer_id") Long customerId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int value) {
        HomePolicy homePolicy = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                homePolicy = new HomePolicy();
//                auto.setValue(value);
//                auto.setDateBuilt(dateBuilt);
//                auto.setCustomer(customer.get());
                homePolicyRepository.save(homePolicy);
            }
        }
        return homePolicy;
    }

}
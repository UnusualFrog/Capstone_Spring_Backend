package org.example.capstone.controllers;

import org.example.capstone.dataaccess.*;
import org.example.capstone.pojos.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.http.HttpStatus;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
    @Autowired private StrongPasswordEncryptor passwordEncryptor; //Choose x-www-form-urlencoded under BODY for register/login POSTS

    private double taxRate = 0.15;

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
     * Registers a new customer account. The password is securely hashed before being saved.
     *
     * @param name           The full name of the customer.
     * @param age            The customer's age.
     * @param accidentCount  The number of past accidents associated with the customer.
     * @param email          The customer's email address.
     * @param username       The desired username for the customer account.
     * @param password       The plain-text password to be hashed and stored.
     * @return A JSON response indicating success or failure.
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerCustomer(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Integer accidentCount,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password
    ) {
        Map<String, Object> response = new HashMap<>();

        if (customerRepository.existsByUsername(username)) {
            response.put("success", false);
            response.put("message", "Username already exists. Please choose a new username.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAge(age);
        customer.setAccidentCount(accidentCount);
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setPassword(passwordEncryptor.encryptPassword(password));

        customerRepository.save(customer);

        response.put("success", true);
        response.put("message", "Customer registration successful!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Authenticates a customer login by verifying the provided credentials against the stored encrypted password.
     *
     * @param username The customer's username.
     * @param password The plain-text password to validate.
     * @return A JSON response indicating login success or failure.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginCustomer(
            @RequestParam String username,
            @RequestParam String password
    ) {
        Map<String, Object> response = new HashMap<>();
        Customer customer = customerRepository.findByUsername(username);

        if (customer != null && passwordEncryptor.checkPassword(password, customer.getPassword())) {
            response.put("message", "Login successful!");
            response.put("customerId", customer.getId());
            response.put("username", customer.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
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
    public @ResponseBody String updateCustomer(
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
     * @return The newly created Auto entity, or null if the user does not exist
     */
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID + RESTNouns.AUTO)
    public @ResponseBody Auto createAutoByCustomer(
            @PathVariable("id") Long customerId,
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam Integer year
            ) {
        Auto auto = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                auto = new Auto();
                auto.setMake(make);
                auto.setModel(model);
                auto.setYear(year);
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
    public @ResponseBody String updateAutoByCustomer(@PathVariable("customer_id") Long userId, @PathVariable("auto_id") Long autoId, @RequestParam LocalDate dateBuilt, @RequestParam int value){
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

    @PutMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.CUSTOMER_ID + RESTNouns.QUOTE_ID)
    public @ResponseBody String updateHomeQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("quote_id") Long policyId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int value){
        if (customerRepository.existsById(customerId) && homeQuoteRepository.existsById(policyId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<HomeQuote> homeQuote = homeQuoteRepository.findById(policyId);
            if(customer.isPresent() && homeQuote.isPresent()){
//                auto.get().setDateBuilt(dateBuilt);
//                auto.get().setValue(value);

                homeQuoteRepository.save(homeQuote.get());
                customerRepository.save(customer.get());
            }
            return "Home Quote with ID " + policyId + " updated successfully.";
        } else {
            return "Home Quote with ID " + policyId + " not found.";
        }
    }

    @DeleteMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER_ID + RESTNouns.QUOTE_ID)
    public @ResponseBody String deleteHomeQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("quote_id") Long quoteId) {
        if (customerRepository.existsById(customerId) && homeQuoteRepository.existsById(quoteId)) {
            homeQuoteRepository.deleteById(quoteId);
            return "Home Quote with ID " + quoteId + " deleted successfully.";
        } else {
            return "Home Quote with ID " + quoteId + " not found.";
        }
    }




    /* *
     *  AUTO QUOTE METHODS
     * */
    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ID)
    public @ResponseBody Optional<AutoQuote> getAutoQuoteById(@PathVariable("id") Long quoteID) {
        return autoQuoteRepository.findById(quoteID);
    }

//    @GetMapping(path = RESTNouns.CUSTOMER +  RESTNouns.ID + RESTNouns.AUTO_QUOTE)
//    public @ResponseBody Iterable<AutoQuote> getAllAutoQuotesByUser(@PathVariable("id") Long customerId) {
//        Iterable<AutoQuote> autoQuotes = null;
//        if (customerRepository.existsById(customerId)) {
//            Optional<Customer> user = customerRepository.findById(customerId);
//            if(user.isPresent()){
////                autoQuotes = autoQuoteRepository.getAllAutoQuotesById(customerId);
//            }
//        }
//
//        //TODO handle errors
//
//        return autoQuotes;
//    }

    @PostMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.CUSTOMER_ID + RESTNouns.AUTO_ID)
    public @ResponseBody String createAutoQuoteForCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("auto_id") Long autoId
    ) {
        Optional<Auto> auto = autoRepository.findById(autoId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (auto.isPresent() && customer.isPresent()) {
            LocalDate today = LocalDate.now();
            double factor = 1;
            if (customer.get().getAge() < 25) {
                factor *= 2;
            }
            if (customer.get().getAccidentCount() > 1) {
                factor *= 2.5;
            } else if (customer.get().getAccidentCount() == 1) {
                factor *= 1.25;
            }
            if (today.getYear() - auto.get().getYear() > 10) {
                factor *= 2;
            } else if (today.getYear() - auto.get().getYear() > 5) {
                factor *= 1.5;
            }
            if (homePolicyRepository.getHomePolicyByCustId(customerId).iterator().hasNext()) {
                double factorAdjust = 1;
                for (HomePolicy policy : homePolicyRepository.getHomePolicyByCustId(customerId)) {
                    if (policy.getActive()) {
                        factorAdjust = 0.9;
                    }
                }
                factor *= factorAdjust;
            }
            double premium = 750 * factor * taxRate;
            AutoQuote quote = new AutoQuote();
            quote.setGenerationDate(today);
            quote.setPremium(premium);
            quote.setTaxRate(taxRate);
            quote.setAuto(auto.get());
        /* Calculation Info
            Base: 750
            Driver Age: <25 = 2; 1
            Accidents: >=2 = 2.5; 1 = 1.25; 1
            Vehicle Age: >10 = 2; >5 = 1.5; 1
            Discount: Active Home = 0.9
            Base * All Factors * Tax Rate
         */
            autoQuoteRepository.save(quote);
            return "Quote Generated!";
        }
        return "Customer or Vehicle not present.";
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

@PutMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER_ID + RESTNouns.POLICY_ID)
    public @ResponseBody String updateHomePolicyByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("policy_id") Long policyId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int value){
        if (customerRepository.existsById(customerId) && homePolicyRepository.existsById(policyId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<HomePolicy> homePolicy = homePolicyRepository.findById(policyId);
            if(customer.isPresent() && homePolicy.isPresent()){
//                auto.get().setDateBuilt(dateBuilt);
//                auto.get().setValue(value);

                homePolicyRepository.save(homePolicy.get());
                customerRepository.save(customer.get());
            }
            return "Home Policy with ID " + policyId + " updated successfully.";
        } else {
            return "Home Policy with ID " + policyId + " not found.";
        }
    }

    @DeleteMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER_ID + RESTNouns.POLICY_ID)
    public @ResponseBody String deleteHomePolicyByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("policy_id") Long policyId) {
        if (customerRepository.existsById(customerId) && homePolicyRepository.existsById(policyId)) {
            homePolicyRepository.deleteById(policyId);
            return "Home Policy with ID " + policyId + " deleted successfully.";
        } else {
            return "Home Policy with ID " + policyId + " not found.";
        }
    }

}
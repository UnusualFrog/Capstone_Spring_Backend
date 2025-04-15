package org.example.capstone.controllers;

import org.example.capstone.dataaccess.*;
import org.example.capstone.pojos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.http.HttpStatus;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
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
    @Autowired private AccidentsRepository accidentsRepository;
    @Autowired private AddressRepository addressRepository;

    private double taxRate = 0.15;
    private double baseAutoPremium = 750;
    private double baseHomePremium = 500;
    private DecimalFormat decimalFormatter = new DecimalFormat("#.##");

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
     * @param birthday       The customer's birthday.
     * @param email          The customer's email address.
     * @param username       The desired username for the customer account.
     * @param password       The plain-text password to be hashed and stored.
     * @return A JSON response indicating success or failure.
     */
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.REGISTER + RESTNouns.ADDRESS_ID)
    public ResponseEntity<Map<String, Object>> createCustomer(
            @PathVariable("address_id") Long addressId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate birthday,
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

        Optional<Address> address = addressRepository.findById(addressId);
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setBirthday(birthday);
        customer.setEmail(email);
        customer.setUsername(username);
        address.ifPresent(customer::setAddress);
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
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.LOGIN)
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
    public @ResponseBody Optional<Customer> getCustomerById(@PathVariable("id") Long customerId) {
        return customerRepository.findById(customerId);
    }

    @GetMapping(path = RESTNouns.CUSTOMER + RESTNouns.NAME)
    public @ResponseBody Iterable<Customer> getAllCustomersByName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        return customerRepository.getAllCustomersByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(path = RESTNouns.CUSTOMER + RESTNouns.EMAIL)
    public @ResponseBody Iterable<Customer> getAllCustomersByEmail(
            @RequestParam String email) {
        return customerRepository.getAllCustomersByEmail(email);
    }

    /**
     * Updates an existing customer's information.
     *
     * @param customerId The unique identifier of the user to update
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody String updateCustomer(
            @PathVariable("id") Long customerId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate birthday,
            @RequestParam String email,
            @RequestParam Long addressId){
        if (customerRepository.existsById(customerId) && addressRepository.existsById(addressId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<Address> address = addressRepository.findById(addressId);
            if(customer.isPresent() && address.isPresent()){
                customer.get().setFirstName(firstName);
                customer.get().setLastName(lastName);
                customer.get().setBirthday(birthday);
                customer.get().setEmail(email);
                customer.get().setAddress(address.get());
                customerRepository.save(customer.get());
            }
            return "Customer with ID " + customerId + " updated successfully.";
        } else {
            return "Customer with ID " + customerId + " not found.";
        }
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

    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.RESET + RESTNouns.ID)
    public ResponseEntity<Map<String, Object>> resetCustomerPassword(
            @PathVariable("id") Long customerId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        Map<String, Object> response = new HashMap<>();
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (passwordEncryptor.checkPassword(oldPassword, customer.getPassword())) {
                customer.setPassword(passwordEncryptor.encryptPassword(newPassword));
                customerRepository.save(customer);
                response.put("success", true);
                response.put("message", "Customer password updated successfully.!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
        response.put("success", false);
        response.put("message", "Account not found.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /* *
     *  EMPLOYEE METHODS
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
    @PostMapping(path = RESTNouns.EMPLOYEE + RESTNouns.LOGIN)
    public ResponseEntity<Map<String, Object>> loginEmployee(
            @RequestParam String username,
            @RequestParam String password
    ) {
        Map<String, Object> response = new HashMap<>();
        Employee employee = employeeRepository.findByUsername(username);

        if (employee != null && passwordEncryptor.checkPassword(password, employee.getPassword())) {
            response.put("message", "Login successful!");
            response.put("employeeId", employee.getId());
            response.put("username", employee.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Updates an existing customer's information.
     *
     * @param employeeId The unique identifier of the user to update
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.EMPLOYEE + RESTNouns.ID)
    public @ResponseBody String updateEmployeeName(
            @PathVariable("id") Long employeeId,
            @RequestParam String firstName,
            @RequestParam String lastName){
        if (employeeRepository.existsById(employeeId)) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if(employee.isPresent()){
                employee.get().setFirstName(firstName);
                employee.get().setLastName(lastName);
                employeeRepository.save(employee.get());
            }
            return "Employee with ID " + employeeId + " updated successfully.";
        } else {
            return "Employee with ID " + employeeId + " not found.";
        }
    }

    /**
     * Updates an existing customer's information.
     *
     * @param customerId The unique identifier of the user to update
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.EMPLOYEE + RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody String employeeUpdateCustomer(
            @PathVariable("id") Long customerId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate birthday,
            @RequestParam String email,
            @RequestParam Long addressId,
            @RequestParam String username,
            @RequestParam String password){
        if (customerRepository.existsById(customerId) && addressRepository.existsById(addressId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<Address> address = addressRepository.findById(addressId);
            if(customer.isPresent() && address.isPresent()){
                customer.get().setFirstName(firstName);
                customer.get().setLastName(lastName);
                customer.get().setBirthday(birthday);
                customer.get().setEmail(email);
                customer.get().setAddress(address.get());
                customer.get().setUsername(username);
                customer.get().setPassword(passwordEncryptor.encryptPassword(password));
                customerRepository.save(customer.get());
            }
            return "Customer with ID " + customerId + " updated successfully.";
        } else {
            return "Customer with ID " + customerId + " not found.";
        }
    }

    @PutMapping(path = RESTNouns.EMPLOYEE + RESTNouns.RESET + RESTNouns.ID)
    public ResponseEntity<Map<String, Object>> resetEmployeePassword(
            @PathVariable("id") Long employeeId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        Map<String, Object> response = new HashMap<>();
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (passwordEncryptor.checkPassword(oldPassword, employee.getPassword())) {
                employee.setPassword(passwordEncryptor.encryptPassword(newPassword));
                employeeRepository.save(employee);
                response.put("success", true);
                response.put("message", "Employee password updated successfully.!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
        response.put("success", false);
        response.put("message", "Account not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /* *
     *  ADMIN METHODS
     * */

    /**
     * Registers a new employee account. The password is securely hashed before being saved.
     *
     * @param email          The customer's email address.
     * @param username       The desired username for the employee account.
     * @param password       The plain-text password to be hashed and stored.
     * @return A JSON response indicating success or failure.
     */
    @PostMapping(path = RESTNouns.ADMIN + RESTNouns.REGISTER)
    public ResponseEntity<Map<String, Object>> adminCreateEmployee(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password
    ) {
        Map<String, Object> response = new HashMap<>();

        if (employeeRepository.existsByUsername(username)) {
            response.put("success", false);
            response.put("message", "Username already exists. Please choose a new username.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(passwordEncryptor.encryptPassword(password));

        employeeRepository.save(employee);

        response.put("success", true);
        response.put("message", "Employee registration successful!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates an existing customer's information.
     *
     * @param employeeId The unique identifier of the user to update
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.ADMIN + RESTNouns.ID)
    public @ResponseBody String adminUpdateEmployee(
            @PathVariable("id") Long employeeId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password){
        if (employeeRepository.existsById(employeeId)) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if(employee.isPresent()){
                employee.get().setFirstName(firstName);
                employee.get().setLastName(lastName);
                employee.get().setEmail(email);
                employee.get().setUsername(username);
                employee.get().setPassword(passwordEncryptor.encryptPassword(password));
                employeeRepository.save(employee.get());
            }
            return "Employee with ID " + employeeId + " updated successfully.";
        } else {
            return "Employee with ID " + employeeId + " not found.";
        }
    }

    /**
     * Deletes a customer from the database by their unique identifier.
     *
     * @param employeeId The unique identifier of the user to delete
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.ADMIN + RESTNouns.ID)
    public @ResponseBody String adminDeleteEmployee(@PathVariable("id") Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return "Employee with ID " + employeeId + " deleted successfully.";
        } else {
            return "Employee with ID " + employeeId + " not found.";
        }
    }

    /* *
     *  HOME METHODS
     * */

    /**
     * Retrieves all homes associated with a specific customer.
     *
     * @param customerId The unique identifier of the customer whose homes are to be retrieved
     * @return An iterable collection of Home entities belonging to the specified customer
     */
    @GetMapping(path = RESTNouns.CUSTOMER +  RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody Iterable<Home> getAllHomesByCustomer(@PathVariable("id") Long customerId) {
        Iterable<Home> homes = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if(customer.isPresent()){
                homes = homeRepository.getAllHomesByCustomer(customer.get());
            }
        }

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
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID + RESTNouns.HOME + RESTNouns.ADDRESS_ID)
    public @ResponseBody Home createHomeByCustomerAndAddress(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("address_id") Long addressId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam Integer homeValue,
            @RequestParam Home.HeatingType heatingType,
            @RequestParam Home.Location location,
            @RequestParam Home.DwellingType dwellingType) {
        Home home = null;
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                home = new Home();
                Optional<Address> address = addressRepository.findById(addressId);
                home.setHomeValue(homeValue);
                home.setDateBuilt(dateBuilt);
                home.setCustomer(customer.get());
                home.setHeatingType(heatingType);
                home.setLocation(location);
                home.setTypeOfDwelling(dwellingType);
                address.ifPresent(home::setAddress);
                homeRepository.save(home);
            }
        }

        return home;
    }

    /**
     * Updates an existing home associated with a specific customer.
     *
     * @param homeId The unique identifier of the home to be updated
     * @param dateBuilt The new date when the home was built
     * @param homeValue The new monetary value of the home
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path =  RESTNouns.HOME + RESTNouns.HOME_ID)
    public @ResponseBody String updateHomeById(
            @PathVariable("home_id") Long homeId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int homeValue,
            @RequestParam Home.HeatingType heatingType,
            @RequestParam Home.Location location,
            @RequestParam Home.DwellingType dwellingType,
            @RequestParam Long customerId,
            @RequestParam Long addressId){
        if (homeRepository.existsById(homeId)) {
            Optional<Home> home = homeRepository.findById(homeId);
            if(home.isPresent()){
                Optional<Customer> customer = customerRepository.findById(customerId);
                Optional<Address> address = addressRepository.findById(addressId);
                if (customer.isPresent() && address.isPresent()) {
                    home.get().setDateBuilt(dateBuilt);
                    home.get().setHomeValue(homeValue);
                    home.get().setHeatingType(heatingType);
                    home.get().setLocation(location);
                    home.get().setTypeOfDwelling(dwellingType);
                    home.get().setCustomer(customer.get());
                    home.get().setAddress(address.get());
                    homeRepository.save(home.get());
                    customerRepository.save(customer.get());
                }
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
    *   ADDRESS METHODS
    * */

    @GetMapping(path = RESTNouns.ADDRESS)
    public @ResponseBody Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping(path = RESTNouns.ADDRESS + RESTNouns.ID)
    public @ResponseBody Optional<Address> getAddressById(@PathVariable("id") Long addressId) {
        return addressRepository.findById(addressId);
    }

    @PostMapping(path = RESTNouns.ADDRESS)
    public @ResponseBody Address createAddress(
            @RequestParam Integer unit,
            @RequestParam String street,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String postalCode) {
        Address address = new Address();
        address.setUnit(unit);
        address.setStreet(street);
        address.setCity(city);
        address.setProvince(province);
        address.setPostalCode(postalCode);
        addressRepository.save(address);

        return address;
    }

    @PutMapping(path = RESTNouns.ADDRESS + RESTNouns.ADDRESS_ID)
        public @ResponseBody String updateAddressById(
                @PathVariable("address_id") Long addressId,
                @RequestParam Integer unit,
                @RequestParam String street,
                @RequestParam String city,
                @RequestParam String province,
                @RequestParam String postalCode){
            if (addressRepository.existsById(addressId)) {
                Optional<Address> address = addressRepository.findById(addressId);
                if(address.isPresent()){
                    address.get().setUnit(unit);
                    address.get().setStreet(street);
                    address.get().setCity(city);
                    address.get().setProvince(province);
                    address.get().setPostalCode(postalCode);
                    addressRepository.save(address.get());
                }
                return "Address with ID " + addressId + " updated successfully.";
            } else {
                return "Address with ID " + addressId + " not found.";
            }
        }

    @DeleteMapping(path = RESTNouns.ADDRESS + RESTNouns.ADDRESS_ID)
    public @ResponseBody String deleteAddressById(
            @PathVariable("address_id") Long addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return "Address with ID " + addressId + " deleted successfully.";
        } else {
            return "Address with ID " + addressId + " not found.";
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
    public @ResponseBody Iterable<Auto> getAllAutosByCustomer(@PathVariable("id") Long customerId) {
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
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID + RESTNouns.AUTO + RESTNouns.AUTO_ID)
    public @ResponseBody String updateAutoByCustomer(
            @PathVariable("customer_id") Long userId,
            @PathVariable("auto_id") Long autoId,
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam Integer year){
        if (customerRepository.existsById(userId) && autoRepository.existsById(autoId)) {
            Optional<Customer> customer = customerRepository.findById(userId);
            Optional<Auto> auto = autoRepository.findById(autoId);
            if(customer.isPresent() && auto.isPresent()){
                auto.get().setMake(make);
                auto.get().setModel(model);
                auto.get().setYear(year);
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
     *  ACCIDENTS METHODS
     * */
    @GetMapping(path = RESTNouns.ACCIDENT)
        public @ResponseBody Iterable<Accident> getAllAccidents() {
            return accidentsRepository.findAll();
        }

    @GetMapping(path = RESTNouns.ACCIDENT + RESTNouns.ID)
        public @ResponseBody Optional<Accident> getAccidentById(@PathVariable("id") Long accidentID) {
            return accidentsRepository.findById(accidentID);
        }

    @PostMapping(path = RESTNouns.ACCIDENT + RESTNouns.CUSTOMER_ID)
        public @ResponseBody Accident createAccidentByCustomer(
                @PathVariable("customer_id") Long customerId,
                @RequestParam LocalDate dateOfAccident) {
            Accident accident = null;
            if (customerRepository.existsById(customerId)) {
                Optional<Customer> customer = customerRepository.findById(customerId);
                if (customer.isPresent()) {
                    accident = new Accident();
                    accident.setCustomer(customer.get());
                    accident.setDate(dateOfAccident);
                    accidentsRepository.save(accident);
                }
            }

            return accident;
        }

    @PutMapping(path = RESTNouns.ACCIDENT + RESTNouns.CUSTOMER_ID + RESTNouns.ACCIDENT_ID)
    public @ResponseBody String updateAccidentById(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("accident_id") Long accidentId,
            @RequestParam LocalDate dateOfAccident){
        if (customerRepository.existsById(customerId) && accidentsRepository.existsById(accidentId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<Accident> accident = accidentsRepository.findById(accidentId);
            if(customer.isPresent() && accident.isPresent()){
                accident.get().setCustomer(customer.get());
                accident.get().setDate(dateOfAccident);
                accidentsRepository.save(accident.get());
            }
            return "Accident with ID " + accidentId + " updated successfully.";
        } else {
            return "Accident with ID " + accidentId + " not found.";
        }
    }

    @DeleteMapping(path = RESTNouns.ACCIDENT + RESTNouns.ACCIDENT_ID)
    public @ResponseBody String deleteAccidentById(
            @PathVariable("accident_id") Long accidentId) {
        if (accidentsRepository.existsById(accidentId)) {
            accidentsRepository.deleteById(accidentId);
            return "Accident with ID " + accidentId + " deleted successfully.";
        } else {
            return "Accident with ID " + accidentId + " not found.";
        }
    }

    /* *
     *  HOME QUOTE METHODS
     * */

    /**
     * Retrieves all home quotes from the database.
     *
     * @return An iterable collection of all HomeQuote entities
     */
    @GetMapping(path = RESTNouns.HOME_QUOTE)
    public @ResponseBody Iterable<HomeQuote> getAllHomeQuotes() {
        return homeQuoteRepository.findAll();
    }

    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ID)
    public @ResponseBody Optional<HomeQuote> getHomeQuoteById(@PathVariable("id") Long quoteID) {
        return homeQuoteRepository.findById(quoteID);
    }

    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<HomeQuote> getAllHomeQuotesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return homeQuoteRepository.getAllByCustId(customerID);
    }

    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ACTIVE + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<HomeQuote> getAllActiveHomeQuotesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return homeQuoteRepository.getAllActiveByCustIdAndActive(customerID, true);
    }

    @PostMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.CUSTOMER_ID + RESTNouns.HOME_ID)
    public @ResponseBody HomeQuote createHomeQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("home_id") Long homeId,
            @RequestParam int liability,
            @RequestParam boolean packagedQuote
    ) {
        HomeQuote quote = null;
        if (customerRepository.existsById(customerId) && homeRepository.existsById(homeId)) {
            Optional<Home> home = homeRepository.findById(homeId);
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (home.isPresent() && customer.isPresent()) {
                LocalDate today = LocalDate.now();
                int homeAge = Period.between(home.get().getDateBuilt(), today).getYears();
                double factor = 1;
                if (liability == 2000000) {
                    factor *= 1.25;
                }
                if (homeAge > 50) {
                    factor *= 1.5;
                } else if (homeAge > 25) {
                    factor *= 1.25;
                }
                if (home.get().getHeatingType() == Home.HeatingType.OIL_HEATING) {
                    factor *= 2;
                } else if (home.get().getHeatingType() == Home.HeatingType.WOOD_HEATING) {
                    factor *= 1.25;
                }
                if (home.get().getLocation() == Home.Location.RURAL) {
                    factor *= 1.15;
                }
                if (packagedQuote) {
                    factor *= 0.9;
                } else if (autoPolicyRepository.getAllByCustId(customerId).iterator().hasNext()) {
                    double factorAdjust = 1;
                    for (AutoPolicy policy : autoPolicyRepository.getAllByCustId(customerId)) {
                        if (policy.getActive()) {
                            factorAdjust = 0.9;
                            break;
                        }
                    }
                    factor *= factorAdjust;
                }
                double addPremium = (home.get().getHomeValue() > 250000) ? home.get().getHomeValue() * 0.002 : 0;
                double premium = (baseHomePremium + addPremium) * factor * (taxRate + 1);
                premium = Double.parseDouble(decimalFormatter.format(premium));
                quote = new HomeQuote();
                quote.setPremium(premium);
                quote.setGenerationDate(today);
                quote.setLiabilityLimit(liability);
                quote.setTaxRate(taxRate);
                quote.setHome(home.get());
                quote.setBasePremium(baseHomePremium);
                quote.setCustId(customer.get().getId());
                homeQuoteRepository.save(quote);
            }
        }
        return quote;
    }


    @PutMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.CUSTOMER_ID + RESTNouns.QUOTE_ID)
    public @ResponseBody String updateHomeQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("quote_id") Long policyId,
            @RequestParam boolean activeStatus){
        if (customerRepository.existsById(customerId) && homeQuoteRepository.existsById(policyId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<HomeQuote> homeQuote = homeQuoteRepository.findById(policyId);
            if(customer.isPresent() && homeQuote.isPresent()){
                homeQuote.get().setActive(activeStatus);

                homeQuoteRepository.save(homeQuote.get());
                customerRepository.save(customer.get());
            }
            return "Home Quote with ID " + policyId + " updated successfully.";
        } else {
            return "Home Quote with ID " + policyId + " not found.";
        }
    }


    /* *
     *  AUTO QUOTE METHODS
     * */

    /**
     * Retrieves all auto quotes from the database.
     *
     * @return An iterable collection of all AutoQuote entities
     */
    @GetMapping(path = RESTNouns.AUTO_QUOTE)
    public @ResponseBody Iterable<AutoQuote> getAllAutoQuotes() {
        return autoQuoteRepository.findAll();
    }

    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ID)
    public @ResponseBody Optional<AutoQuote> getAutoQuoteById(@PathVariable("id") Long quoteID) {
        return autoQuoteRepository.findById(quoteID);
    }

    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<AutoQuote> getAllAutoQuotesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return autoQuoteRepository.getAllByCustId(customerID);
    }

    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ACTIVE + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<AutoQuote> getAllActiveAutoQuotesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return autoQuoteRepository.getAllActiveByCustIdAndActive(customerID, true);
    }

    @PostMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.CUSTOMER_ID + RESTNouns.AUTO_ID)
    public @ResponseBody AutoQuote createAutoQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("auto_id") Long autoId,
            @RequestParam boolean packagedQuote) {
        AutoQuote quote = null;
        if (customerRepository.existsById(customerId) && autoRepository.existsById(autoId)) {
            Optional<Auto> auto = autoRepository.findById(autoId);
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (auto.isPresent() && customer.isPresent()) {
                LocalDate today = LocalDate.now();
                int age = Period.between(customer.get().getBirthday(), today).getYears();
                double factor = 1;
                if (age < 25) {
                    factor *= 2;
                }
                int accidentCount = 0;
                Iterable<Accident> accidents = accidentsRepository.getAllAccidentsByCustomer(customer.get());
                for (Accident accident : accidents) {
                    if (accident.getDate().isAfter(today.minusYears(5))) {
                        accidentCount++;
                    }
                }
                if (accidentCount > 1) {
                    factor *= 2.5;
                } else if (accidentCount == 1) {
                    factor *= 1.25;
                }
                if (today.getYear() - auto.get().getYear() > 10) {
                    factor *= 2;
                } else if (today.getYear() - auto.get().getYear() > 5) {
                    factor *= 1.5;
                }
                if (packagedQuote) {
                    factor *= 0.9;
                } else if (homePolicyRepository.getAllByCustId(customerId).iterator().hasNext()) {
                    double factorAdjust = 1;
                    for (HomePolicy policy : homePolicyRepository.getAllByCustId(customerId)) {
                        if (policy.getActive()) {
                            factorAdjust = 0.9;
                            break;
                        }
                    }
                    factor *= factorAdjust;
                }
                double premium = baseAutoPremium * factor * (taxRate + 1);
                premium = Double.parseDouble(decimalFormatter.format(premium));
                quote = new AutoQuote();
                quote.setGenerationDate(today);
                quote.setPremium(premium);
                quote.setTaxRate(taxRate);
                quote.setAuto(auto.get());
                quote.setBasePremium(baseAutoPremium);
                quote.setCustId(customer.get().getId());
                autoQuoteRepository.save(quote);
            }
        }
        return quote;
    }

    @PutMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.CUSTOMER_ID + RESTNouns.QUOTE_ID)
    public @ResponseBody String updateAutoQuoteByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("quote_id") Long policyId,
            @RequestParam boolean activeStatus) {
        if (customerRepository.existsById(customerId) && autoQuoteRepository.existsById(policyId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<AutoQuote> autoQuote = autoQuoteRepository.findById(policyId);
            if(customer.isPresent() && autoQuote.isPresent()){
                autoQuote.get().setActive(activeStatus);

                autoQuoteRepository.save(autoQuote.get());
                customerRepository.save(customer.get());
            }
            return "Auto Quote with ID " + policyId + " updated successfully.";
        } else {
            return "Auto Quote with ID " + policyId + " not found.";
        }
    }


    /* *
     *  HOME POLICY METHODS
     * */

    /**
     * Retrieves all home policies from the database.
     *
     * @return An iterable collection of all HomePolicy entities
     */
    @GetMapping(path = RESTNouns.HOME_POLICY)
    public @ResponseBody Iterable<HomePolicy> getAllHomePolicies() {
        return homePolicyRepository.findAll();
    }

    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ID)
    public @ResponseBody Optional<HomePolicy> getHomePolicyById(@PathVariable("id") Long quoteID) {
        return homePolicyRepository.findById(quoteID);
    }

    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<HomePolicy> getAllHomePoliciesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return homePolicyRepository.getAllByCustId(customerID);
    }

    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ACTIVE + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<HomePolicy> getAllActiveHomePoliciesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return homePolicyRepository.getAllActiveByCustIdAndActive(customerID, true);
    }

    @PostMapping(path = RESTNouns.HOME_POLICY + RESTNouns.QUOTE_ID)
    public @ResponseBody HomePolicy createHomePolicyByHomeQuote(
            @PathVariable("quote_id") Long quoteId,
            @RequestParam LocalDate effectiveDate) {
        Optional<HomeQuote> quoteOptional = homeQuoteRepository.findById(quoteId);
        HomePolicy policy = null;
        if (quoteOptional.isPresent()) {
            HomeQuote quote = quoteOptional.get();
            policy = new HomePolicy();
            policy.setEffectiveDate(effectiveDate);
            policy.setEndDate(effectiveDate.plusYears(1));
            policy.setPremium(quote.getPremium());
            policy.setLiabilityLimit(quote.getLiabilityLimit());
            policy.setTaxRate(quote.getTaxRate());
            policy.setHome(quote.getHome());
            policy.setCustId(quote.getCustId());
            policy.setBasePremium(quote.getBasePremium());
            homePolicyRepository.save(policy);
        }
        return policy;
    }

    @PutMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER_ID + RESTNouns.POLICY_ID)
    public @ResponseBody String updateHomePolicyByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("policy_id") Long policyId,
            @RequestParam boolean activeStatus,
            @RequestParam LocalDate endDate){
        if (customerRepository.existsById(customerId) && homePolicyRepository.existsById(policyId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<HomePolicy> homePolicy = homePolicyRepository.findById(policyId);
            if(customer.isPresent() && homePolicy.isPresent()){
                homePolicy.get().setActive(activeStatus);
                homePolicy.get().setEndDate(endDate);

                homePolicyRepository.save(homePolicy.get());
                customerRepository.save(customer.get());
            }
            return "Home Policy with ID " + policyId + " updated successfully.";
        } else {
            return "Home Policy with ID " + policyId + " not found.";
        }
    }


    /* *
     *  AUTO POLICY METHODS
     * */

    /**
     * Retrieves all auto policies from the database.
     *
     * @return An iterable collection of all AutoPolicy entities
     */
    @GetMapping(path = RESTNouns.AUTO_POLICY)
    public @ResponseBody Iterable<AutoPolicy> getAllAutoPolicies() {
        return autoPolicyRepository.findAll();
    }

    @GetMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.ID)
    public @ResponseBody Optional<AutoPolicy> getAutoPolicyById(@PathVariable("id") Long quoteID) {
        return autoPolicyRepository.findById(quoteID);
    }

    @GetMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.CUSTOMER + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<AutoPolicy> getAllAutoPoliciesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return autoPolicyRepository.getAllByCustId(customerID);
    }

    @GetMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.ACTIVE + RESTNouns.CUSTOMER_ID)
    public @ResponseBody Iterable<AutoPolicy> getAllActiveAutoPoliciesByCustomerId(@PathVariable("customer_id") Long customerID) {
        return autoPolicyRepository.getAllActiveByCustIdAndActive(customerID, true);
    }

    @PostMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.QUOTE_ID)
    public @ResponseBody AutoPolicy createAutoPolicyByAutoQuote(
            @PathVariable("quote_id") Long quoteId,
            @RequestParam LocalDate effectiveDate) {
        Optional<AutoQuote> quoteOptional = autoQuoteRepository.findById(quoteId);
        AutoPolicy policy = null;
        if (quoteOptional.isPresent()) {
            AutoQuote quote = quoteOptional.get();
            policy = new AutoPolicy();
            policy.setEffectiveDate(effectiveDate);
            policy.setEndDate(effectiveDate.plusYears(1));
            policy.setPremium(quote.getPremium());
            policy.setTaxRate(quote.getTaxRate());
            policy.setAuto(quote.getAuto());
            policy.setCustId(quote.getCustId());
            policy.setBasePremium(quote.getBasePremium());
            autoPolicyRepository.save(policy);
        }
        return policy;
    }

    @PutMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.CUSTOMER_ID + RESTNouns.POLICY_ID)
    public @ResponseBody String updateAutoPolicyByCustomer(
            @PathVariable("customer_id") Long customerId,
            @PathVariable("policy_id") Long policyId,
            @RequestParam boolean activeStatus,
            @RequestParam LocalDate endDate){
        if (customerRepository.existsById(customerId) && autoPolicyRepository.existsById(policyId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<AutoPolicy> autoPolicy = autoPolicyRepository.findById(policyId);
            if(customer.isPresent() && autoPolicy.isPresent()){
                autoPolicy.get().setActive(activeStatus);
                autoPolicy.get().setEndDate(endDate);

                autoPolicyRepository.save(autoPolicy.get());
                customerRepository.save(customer.get());
            }
            return "Auto Policy with ID " + policyId + " updated successfully.";
        } else {
            return "Home Policy with ID " + policyId + " not found.";
        }
    }

}
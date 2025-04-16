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
    @Autowired private RiskFactors riskFactors;

    // TODO Create a method of storing, adjusting, and loading risk factor as variables or properties.
    private double taxRate = 0.15;
    private double baseAutoPremium = 750;
    private double baseHomePremium = 500;
    private DecimalFormat decimalFormatter = new DecimalFormat("#.##");

    /* *
     *  CUSTOMER METHODS
     * */

    /**
     * Retrieves all customers from the database.
     *
     * @return An iterable collection of all User entities
     */
    @GetMapping(path = RESTNouns.CUSTOMER)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllCustomers() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All customers retrieved");
        response.put("object", customerRepository.findAll());
        System.out.println(riskFactors.getAutoBasePremium());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param customerId The unique identifier of the user to retrieve
     * @return An Optional containing the User if found, or an empty Optional
     */
    @GetMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable("id") Long customerId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All customers with the ID " + customerId + " retrieved");
        response.put("object", customerRepository.findById(customerId));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.CUSTOMER + RESTNouns.NAME)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllCustomersUsingName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All customers named " + firstName + " " + lastName + " retrieved");
        response.put("object", customerRepository.getAllCustomersByFirstNameAndLastName(firstName, lastName));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.CUSTOMER + RESTNouns.EMAIL)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllCustomersUsingEmail(
            @RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All customers with the email " + email + " retrieved");
        response.put("object", customerRepository.getAllCustomersByEmail(email));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
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
    @PostMapping(path = RESTNouns.CUSTOMER + RESTNouns.REGISTER)
    public ResponseEntity<Map<String, Object>> createCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate birthday,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Long addressId) {
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
            @RequestParam String password) {
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
     * Updates an existing customer's information.
     *
     * @param customerId The unique identifier of the user to update
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateCustomerById(
            @PathVariable("id") Long customerId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate birthday,
            @RequestParam String email,
            @RequestParam Long addressId){
        Map<String, Object> response = new HashMap<>();
        if (customerRepository.existsById(customerId)) {
            if (addressRepository.existsById(addressId)) {
                Optional<Customer> customer = customerRepository.findById(customerId);
                Optional<Address> address = addressRepository.findById(addressId);
                if (customer.isPresent() && address.isPresent()) {
                    customer.get().setFirstName(firstName);
                    customer.get().setLastName(lastName);
                    customer.get().setBirthday(birthday);
                    customer.get().setEmail(email);
                    customer.get().setAddress(address.get());
                    customerRepository.save(customer.get());
                }
                response.put("success", true);
                response.put("message", "Customer with ID " + customerId + " updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("success", false);
            response.put("message", "Address with ID " + addressId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = RESTNouns.CUSTOMER + RESTNouns.RESET + RESTNouns.ID)
    public ResponseEntity<Map<String, Object>> resetCustomerPasswordById(
            @PathVariable("id") Long customerId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
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
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a customer from the database by their unique identifier.
     *
     * @param customerId The unique identifier of the user to delete
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteCustomerById(@PathVariable("id") Long customerId) {
        Map<String, Object> response = new HashMap<>();
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            response.put("success", true);
            response.put("message", "Customer with ID " + customerId + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.GONE);
        } else {
            response.put("success", true);
            response.put("message", "Customer with ID " + customerId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All employees retrieved");
        response.put("object", employeeRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping(path = RESTNouns.EMPLOYEE + RESTNouns.LOGIN)
    public ResponseEntity<Map<String, Object>> loginEmployee(
            @RequestParam String username,
            @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        Employee employee = employeeRepository.findByUsername(username);

        if (employee != null && passwordEncryptor.checkPassword(password, employee.getPassword())) {
            response.put("message", "Login successful!");
            response.put("object", employee);
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
    public @ResponseBody ResponseEntity<Map<String, Object>> updateEmployeeNameById(
            @PathVariable("id") Long employeeId,
            @RequestParam String firstName,
            @RequestParam String lastName){
        Map<String, Object> response = new HashMap<>();
        if (employeeRepository.existsById(employeeId)) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if(employee.isPresent()){
                employee.get().setFirstName(firstName);
                employee.get().setLastName(lastName);
                employeeRepository.save(employee.get());
            }
            response.put("success", true);
            response.put("message", "Employee with ID " + employeeId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Employee with ID " + employeeId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing customer's information.
     *
     * @param customerId The unique identifier of the user to update
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.EMPLOYEE + RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> employeeUpdateCustomerById(
            @PathVariable("id") Long customerId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate birthday,
            @RequestParam String email,
            @RequestParam Long addressId,
            @RequestParam String username,
            @RequestParam String password){
        Map<String, Object> response = new HashMap<>();
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
            response.put("success", true);
            response.put("message", "Customer with ID " + customerId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Customer with ID " + customerId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = RESTNouns.EMPLOYEE + RESTNouns.RESET + RESTNouns.ID)
    public ResponseEntity<Map<String, Object>> resetEmployeePasswordById(
            @PathVariable("id") Long employeeId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
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

    @GetMapping(path = RESTNouns.ADMIN + RESTNouns.RISK)
    public @ResponseBody ResponseEntity<Map<String, Object>> adminGetRiskFactors() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All riskFactors retrieved");
        response.put("object", riskFactors);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

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
            @RequestParam String password) {
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
    public @ResponseBody ResponseEntity<Map<String, Object>> adminUpdateEmployeeById(
            @PathVariable("id") Long employeeId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password){
        Map<String, Object> response = new HashMap<>();
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
            response.put("success", true);
            response.put("message", "Employee with ID " + employeeId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Employee with ID " + employeeId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = RESTNouns.ADMIN + RESTNouns.RISK)
    public @ResponseBody ResponseEntity<Map<String, Object>> adminUpdateRiskFactors(@RequestBody RiskFactors rf) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", rf.getHomeValueBaseLine());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    /**
     * Deletes a customer from the database by their unique identifier.
     *
     * @param employeeId The unique identifier of the user to delete
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.ADMIN + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> adminDeleteEmployeeById(@PathVariable("id") Long employeeId) {
        Map<String, Object> response = new HashMap<>();
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            response.put("success", true);
            response.put("message", "Employee with ID " + employeeId + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.GONE);
        } else {
            response.put("success", false);
            response.put("message", "Employee with ID " + employeeId + " not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /* *
     *  HOME METHODS
     * */

    @GetMapping(path = RESTNouns.HOME)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllHomes() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All homes retrieved");
        response.put("object", homeRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all homes associated with a specific customer.
     *
     * @param customerId The unique identifier of the customer whose homes are to be retrieved
     * @return An iterable collection of Home entities belonging to the specified customer
     */

    @GetMapping(path = RESTNouns.HOME +  RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllHomesByCustomerId(@PathVariable("id") Long customerId) {
        Map<String, Object> response = new HashMap<>();
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if(customer.isPresent()){
                response.put("success", true);
                response.put("message", "All homes of customer ID "+ customerId +" retrieved");
                response.put("object", homeRepository.getAllHomesByCustomer(customer.get()));
                return new ResponseEntity<>(response, HttpStatus.FOUND);
            }
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a new home for a specific customer.
     *
     * @param customerId The unique identifier of the user for whom the home is being created
     * @param dateBuilt The date when the home was built
     * @param homeValue The monetary value of the home
     * @return The newly created Home entity, or null if the user does not exist
     */
    @PostMapping(path = RESTNouns.HOME + RESTNouns.ID + RESTNouns.ADDITIONAL_ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> createHomeByAddressAndCustomerIds(
            @PathVariable("id") Long addressId,
            @PathVariable("additional_id") Long customerId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam Integer homeValue,
            @RequestParam Home.HeatingType heatingType,
            @RequestParam Home.Location location,
            @RequestParam Home.DwellingType dwellingType) {
        Map<String, Object> response = new HashMap<>();
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                Home home = new Home();
                Optional<Address> address = addressRepository.findById(addressId);
                home.setHomeValue(homeValue);
                home.setDateBuilt(dateBuilt);
                home.setCustomer(customer.get());
                home.setHeatingType(heatingType);
                home.setLocation(location);
                home.setTypeOfDwelling(dwellingType);
                address.ifPresent(home::setAddress);
                homeRepository.save(home);
                response.put("success", true);
                response.put("message", "Home created successfully.");
                response.put("object", home);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Updates an existing home associated with a specific customer.
     *
     * @param homeId The unique identifier of the home to be updated
     * @param dateBuilt The new date when the home was built
     * @param homeValue The new monetary value of the home
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path =  RESTNouns.HOME + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateHomeById(
            @PathVariable("id") Long homeId,
            @RequestParam LocalDate dateBuilt,
            @RequestParam int homeValue,
            @RequestParam Home.HeatingType heatingType,
            @RequestParam Home.Location location,
            @RequestParam Home.DwellingType dwellingType,
            @RequestParam Long customerId,
            @RequestParam Long addressId){
        Map<String, Object> response = new HashMap<>();
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
            response.put("success", true);
            response.put("message", "Home with ID " + homeId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Home with ID " + homeId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a specific home associated with a user.
     *
     * @param homeId The unique identifier of the home to be deleted
     * @return A string message indicating the result of the deletion operation
     */

    @DeleteMapping(path = RESTNouns.HOME + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteHomeById( @PathVariable("id") Long homeId) {
        Map<String, Object> response = new HashMap<>();
        if (homeRepository.existsById(homeId)) {
            homeRepository.deleteById(homeId);
            response.put("success", true);
            response.put("message", "Home with ID " + homeId + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.GONE);
        } else {
            response.put("success", false);
            response.put("message", "Home with ID " + homeId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /* *
    *   ADDRESS METHODS
    * */

    @GetMapping(path = RESTNouns.ADDRESS)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAddresses() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All addresses retrieved");
        response.put("object", addressRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.ADDRESS + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAddressById(@PathVariable("id") Long addressId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Address with ID "+ addressId +" retrieved");
        response.put("object", addressRepository.findById(addressId));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping(path = RESTNouns.ADDRESS)
    public @ResponseBody ResponseEntity<Map<String, Object>> createAddress(
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
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Address created successfully");
        response.put("object", address);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = RESTNouns.ADDRESS + RESTNouns.ID)
        public @ResponseBody ResponseEntity<Map<String, Object>> updateAddressById(
                @PathVariable("id") Long addressId,
                @RequestParam Integer unit,
                @RequestParam String street,
                @RequestParam String city,
                @RequestParam String province,
                @RequestParam String postalCode){
        Map<String, Object> response = new HashMap<>();
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
                response.put("success", true);
                response.put("message", "Address with ID " + addressId + " updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Address with ID " + addressId + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

    @DeleteMapping(path = RESTNouns.ADDRESS + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteAddressById(
            @PathVariable("id") Long addressId) {
        Map<String, Object> response = new HashMap<>();
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            response.put("success", true);
            response.put("message", "Address with ID " + addressId + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.GONE);
        } else {
            response.put("success", false);
            response.put("message", "Address with ID " + addressId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /* *
     *  AUTO METHODS
     * */

    @GetMapping(path = RESTNouns.AUTO)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAutos() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All autos retrieved");
        response.put("object", autoRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all auto objects associated with a specific user.
     *
     * @param customerId The unique identifier of the user whose auto objects are to be retrieved
     * @return An iterable collection of Auto entities belonging to the specified user,
     *         or null if the user does not exist
     */

    @GetMapping(path = RESTNouns.AUTO +  RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAutosByCustomerId(@PathVariable("id") Long customerId) {
        Map<String, Object> response = new HashMap<>();
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if(customer.isPresent()){
                response.put("success", true);
                response.put("message", "All autos with customer ID "+ customerId +" retrieved");
                response.put("object", autoRepository.getAllByCustomerId(customerId));
                return new ResponseEntity<>(response, HttpStatus.FOUND);
            }
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a new auto object for a specific user.
     *
     * @param customerId The unique identifier of the user for whom the auto is being created
     * @return The newly created Auto entity, or null if the user does not exist
     */

    @PostMapping(path = RESTNouns.AUTO + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> createAutoByCustomerID(
            @PathVariable("id") Long customerId,
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam Integer year) {
        Map<String, Object> response = new HashMap<>();
        if (customerRepository.existsById(customerId)) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                Auto auto = new Auto();
                auto.setMake(make);
                auto.setModel(model);
                auto.setYear(year);
                auto.setCustomer(customer.get());
                autoRepository.save(auto);
                response.put("success", true);
                response.put("message", "Auto created successfully");
//                response.put("object", auto);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Updates an existing auto object associated with a specific user.
     *
     * @param autoId The unique identifier of the auto to be updated
     * @return A string message indicating the result of the update operation
     */

    @PutMapping(path = RESTNouns.AUTO + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateAutoById(
            @PathVariable("id") Long autoId,
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam Integer year){
        Map<String, Object> response = new HashMap<>();
        if (autoRepository.existsById(autoId)) {
            Optional<Auto> auto = autoRepository.findById(autoId);
            if(auto.isPresent()){
                auto.get().setMake(make);
                auto.get().setModel(model);
                auto.get().setYear(year);
                autoRepository.save(auto.get());
            }
            response.put("success", true);
            response.put("message", "Auto with ID " + autoId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("success", false);
        response.put("message", "Auto with ID " + autoId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a specific auto object associated with a customer.
     *
     * @param autoId The unique identifier of the auto to be deleted
     * @return A string message indicating the result of the deletion operation
     */

    @DeleteMapping(path = RESTNouns.AUTO + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteAutoByCustomerId(@PathVariable("id") Long autoId) {
        Map<String, Object> response = new HashMap<>();
        if (autoRepository.existsById(autoId)) {
            autoRepository.deleteById(autoId);
            response.put("success", true);
            response.put("message", "Auto with ID " + autoId + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.GONE);
        }
        response.put("success", false);
        response.put("message", "Auto with ID " + autoId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /* *
     *  ACCIDENTS METHODS
     * */
    @GetMapping(path = RESTNouns.ACCIDENT)
        public @ResponseBody ResponseEntity<Map<String, Object>> getAllAccidents() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All accidents retrieved");
        response.put("object", accidentsRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
        }

    @GetMapping(path = RESTNouns.ACCIDENT + RESTNouns.ID)
        public @ResponseBody ResponseEntity<Map<String, Object>> getAccidentById(@PathVariable("id") Long accidentID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All accidents with ID "+ accidentID +" retrieved");
        response.put("object", accidentsRepository.findById(accidentID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
        }

    @PostMapping(path = RESTNouns.ACCIDENT + RESTNouns.ID)
        public @ResponseBody ResponseEntity<Map<String, Object>> createAccidentByCustomerId(
                @PathVariable("id") Long customerId,
                @RequestParam LocalDate dateOfAccident) {
            Map<String, Object> response = new HashMap<>();
            if (customerRepository.existsById(customerId)) {
                Optional<Customer> customer = customerRepository.findById(customerId);
                if (customer.isPresent()) {
                    Accident accident = new Accident();
                    accident.setCustomer(customer.get());
                    accident.setDate(dateOfAccident);
                    accidentsRepository.save(accident);
                    response.put("success", true);
                    response.put("message", "Accident created successfully");
//                    response.put("object", accident);
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                }
            }
            response.put("success", false);
            response.put("message", "Customer with ID " + customerId + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    @PutMapping(path = RESTNouns.ACCIDENT + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateAccidentById(
            @PathVariable("id") Long accidentId,
            @RequestParam LocalDate dateOfAccident){
        Map<String, Object> response = new HashMap<>();
        if (accidentsRepository.existsById(accidentId)) {
            Optional<Accident> accident = accidentsRepository.findById(accidentId);
            if(accident.isPresent()){
                accident.get().setDate(dateOfAccident);
                accidentsRepository.save(accident.get());
            }
            response.put("success", true);
            response.put("message", "Accident with ID " + accidentId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("success", false);
        response.put("message", "Accident with ID " + accidentId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = RESTNouns.ACCIDENT + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> deleteAccidentById(
            @PathVariable("id") Long accidentId) {
        Map<String, Object> response = new HashMap<>();
        if (accidentsRepository.existsById(accidentId)) {
            accidentsRepository.deleteById(accidentId);
            response.put("success", true);
            response.put("message", "Accident with ID " + accidentId + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.GONE);
        }
        response.put("success", false);
        response.put("message", "Accident with ID " + accidentId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllHomeQuotes() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All home quotes retrieved");
        response.put("object", homeQuoteRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getHomeQuoteById(@PathVariable("id") Long quoteID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Home quote with ID "+ quoteID +" retrieved");
        response.put("object", homeQuoteRepository.findById(quoteID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllHomeQuotesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All home quotes with customer ID " + customerID + " retrieved");
        response.put("object", homeQuoteRepository.getAllByCustId(customerID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ACTIVE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllActiveHomeQuotesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All active home quotes with customer ID " + customerID + " retrieved");
        response.put("object", homeQuoteRepository.getAllActiveByCustIdAndActive(customerID, true));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ID + RESTNouns.ADDITIONAL_ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> createHomeQuoteByCustomerAndHomeId(
            @PathVariable("id") Long customerId,
            @PathVariable("additional_id") Long homeId,
            @RequestParam int liability,
            @RequestParam boolean packagedQuote) {
        Map<String, Object> response = new HashMap<>();
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
                HomeQuote quote = new HomeQuote();
                quote.setPremium(premium);
                quote.setGenerationDate(today);
                quote.setLiabilityLimit(liability);
                quote.setTaxRate(taxRate);
                quote.setHome(home.get());
                quote.setBasePremium(baseHomePremium);
                quote.setCustId(customer.get().getId());
                homeQuoteRepository.save(quote);
                response.put("success", true);
                response.put("message", "Home Quote created successfully");
//                response.put("object", quote);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = RESTNouns.HOME_QUOTE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateHomeQuoteById(
            @PathVariable("id") Long quoteId,
            @RequestParam boolean activeStatus){
        Map<String, Object> response = new HashMap<>();
        if (homeQuoteRepository.existsById(quoteId)) {
            Optional<HomeQuote> homeQuote = homeQuoteRepository.findById(quoteId);
            if(homeQuote.isPresent()){
                homeQuote.get().setActive(activeStatus);
                homeQuoteRepository.save(homeQuote.get());
            }
            response.put("success", true);
            response.put("message", "Home Quote with ID " + quoteId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("success", false);
        response.put("message", "Home Quote with ID " + quoteId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAutoQuotes() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All auto quotes retrieved");
        response.put("object", autoQuoteRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAutoQuoteById(@PathVariable("id") Long quoteID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Auto quote with ID " + quoteID + " retrieved");
        response.put("object", autoQuoteRepository.findById(quoteID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAutoQuotesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All auto quotes with customer ID " + customerID + " retrieved");
        response.put("object", autoQuoteRepository.getAllByCustId(customerID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ACTIVE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllActiveAutoQuotesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All active auto quotes with customer ID " + customerID + " retrieved");
        response.put("object", autoQuoteRepository.getAllActiveByCustIdAndActive(customerID, true));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ID + RESTNouns.ADDITIONAL_ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> createAutoQuoteByCustomerAndAutoId(
            @PathVariable("id") Long customerId,
            @PathVariable("additional_id") Long autoId,
            @RequestParam boolean packagedQuote) {
        Map<String, Object> response = new HashMap<>();
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
                AutoQuote quote = new AutoQuote();
                quote.setGenerationDate(today);
                quote.setPremium(premium);
                quote.setTaxRate(taxRate);
                quote.setAuto(auto.get());
                quote.setBasePremium(baseAutoPremium);
                quote.setCustId(customer.get().getId());
                autoQuoteRepository.save(quote);
                response.put("success", true);
                response.put("message", "Auto Quote created successfully");
//                response.put("object", quote);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        response.put("success", false);
        response.put("message", "Customer with ID " + customerId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = RESTNouns.AUTO_QUOTE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateAutoQuoteById(
            @PathVariable("id") Long quoteId,
            @RequestParam boolean activeStatus) {
        Map<String, Object> response = new HashMap<>();
        if (autoQuoteRepository.existsById(quoteId)) {
            Optional<AutoQuote> autoQuote = autoQuoteRepository.findById(quoteId);
            if(autoQuote.isPresent()){
                autoQuote.get().setActive(activeStatus);
                autoQuoteRepository.save(autoQuote.get());
            }
            response.put("success", true);
            response.put("message", "Auto Quote with ID " + quoteId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("success", false);
        response.put("message", "Auto Quote with ID " + quoteId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllHomePolicies() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All home policies retrieved");
        response.put("object", homePolicyRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getHomePolicyById(@PathVariable("id") Long policyId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Home policy with ID " + policyId + " retrieved");
        response.put("object", homePolicyRepository.findById(policyId));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllHomePoliciesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All home policies with customer ID " + customerID + " retrieved");
        response.put("object", homePolicyRepository.getAllByCustId(customerID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ACTIVE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllActiveHomePoliciesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All active home policies with customer ID " + customerID + " retrieved");
        response.put("object", homePolicyRepository.getAllActiveByCustIdAndActive(customerID, true));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> createHomePolicyByHomeQuoteId(
            @PathVariable("id") Long quoteId,
            @RequestParam LocalDate effectiveDate) {
        Map<String, Object> response = new HashMap<>();
        Optional<HomeQuote> quoteOptional = homeQuoteRepository.findById(quoteId);
        if (quoteOptional.isPresent()) {
            HomeQuote quote = quoteOptional.get();
            HomePolicy policy = new HomePolicy();
            policy.setEffectiveDate(effectiveDate);
            policy.setEndDate(effectiveDate.plusYears(1));
            policy.setPremium(quote.getPremium());
            policy.setLiabilityLimit(quote.getLiabilityLimit());
            policy.setTaxRate(quote.getTaxRate());
            policy.setHome(quote.getHome());
            policy.setCustId(quote.getCustId());
            policy.setBasePremium(quote.getBasePremium());
            homePolicyRepository.save(policy);
            quote.setActive(false);
            homeQuoteRepository.save(quote);
            response.put("success", true);
            response.put("message", "Home Policy created successfully");
//            response.put("object", policy);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response.put("success", false);
        response.put("message", "Home Quote with ID " + quoteId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = RESTNouns.HOME_POLICY + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateHomePolicyById(
            @PathVariable("id") Long policyId,
            @RequestParam boolean activeStatus,
            @RequestParam LocalDate endDate){
        Map<String, Object> response = new HashMap<>();
        if (homePolicyRepository.existsById(policyId)) {
            Optional<HomePolicy> homePolicy = homePolicyRepository.findById(policyId);
            if(homePolicy.isPresent()){
                homePolicy.get().setActive(activeStatus);
                homePolicy.get().setEndDate(endDate);
                homePolicyRepository.save(homePolicy.get());
            }
            response.put("success", true);
            response.put("message", "Home Policy with ID " + policyId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("success", false);
        response.put("message", "Home Policy with ID " + policyId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAutoPolicies() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All auto policies retrieved");
        response.put("object", autoPolicyRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAutoPolicyById(@PathVariable("id") Long policyId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Auto policy with ID " + policyId + " retrieved");
        response.put("object", autoPolicyRepository.findById(policyId));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.CUSTOMER + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllAutoPoliciesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All auto policies with customer ID " + customerID + " retrieved");
        response.put("object", autoPolicyRepository.getAllByCustId(customerID));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.ACTIVE + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> getAllActiveAutoPoliciesByCustomerId(@PathVariable("id") Long customerID) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "All active auto policies with customer ID " + customerID + " retrieved");
        response.put("object", autoPolicyRepository.getAllActiveByCustIdAndActive(customerID, true));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> createAutoPolicyByAutoQuote(
            @PathVariable("id") Long quoteId,
            @RequestParam LocalDate effectiveDate) {
        Map<String, Object> response = new HashMap<>();
        Optional<AutoQuote> quoteOptional = autoQuoteRepository.findById(quoteId);
        if (quoteOptional.isPresent()) {
            AutoQuote quote = quoteOptional.get();
            AutoPolicy policy = new AutoPolicy();
            policy.setEffectiveDate(effectiveDate);
            policy.setEndDate(effectiveDate.plusYears(1));
            policy.setPremium(quote.getPremium());
            policy.setTaxRate(quote.getTaxRate());
            policy.setAuto(quote.getAuto());
            policy.setCustId(quote.getCustId());
            policy.setBasePremium(quote.getBasePremium());
            autoPolicyRepository.save(policy);
            quote.setActive(false);
            autoQuoteRepository.save(quote);
            response.put("success", true);
            response.put("message", "Auto Policy created successfully");
//            response.put("object", policy);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response.put("success", false);
        response.put("message", "Auto Quote with ID " + quoteId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = RESTNouns.AUTO_POLICY + RESTNouns.ID)
    public @ResponseBody ResponseEntity<Map<String, Object>> updateAutoPolicyByCustomer(
            @PathVariable("id") Long policyId,
            @RequestParam boolean activeStatus,
            @RequestParam LocalDate endDate){
        Map<String, Object> response = new HashMap<>();
        if (autoPolicyRepository.existsById(policyId)) {
            Optional<AutoPolicy> autoPolicy = autoPolicyRepository.findById(policyId);
            if(autoPolicy.isPresent()){
                autoPolicy.get().setActive(activeStatus);
                autoPolicy.get().setEndDate(endDate);
                autoPolicyRepository.save(autoPolicy.get());
            }
            response.put("success", true);
            response.put("message", "Auto Policy with ID " + policyId + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("success", false);
        response.put("message", "Auto Policy with ID " + policyId + " not found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
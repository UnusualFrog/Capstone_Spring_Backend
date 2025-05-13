
# Taylor Insurance Spring Backend

A Mock Spring Backend for the hypothetical insurance company Taylor Insurance. 

Built with Intellij using Java and Spring

Connects with the following frontend to manage administration as well as creation, storage and management of user policies, quotes, vehicles and homes.


## Setup

Once the repo is cloned, a HeidiSQL database will need to be created. The database's port, name, username and password must be specified in java/resources/application.properties

```
spring.datasource.url=jdbc:mariadb://localhost:3307/capstone
spring.datasource.username=usernameHere
spring.datasource.password=passwordHere
```


## Usage/Examples

The main controller contains routes for all neccesary CRUD operations for working with all entities within the database system (ex. User, Home, Auto, HomeQuote, AutoPolicy, etc.)

User accounts can be created and assocaited with Home and/or Auto entities. Once created, quotes can be generated for each entity and once a quote is generated, it can be approved for a policy.

User accounts created within the system are automatically hashed in the database using SHA-256 encryption.

Employee accounts can be used to update details about customers, insurable entities, quotes and policies.

Employee accounts can be created by Admin accounts, Admin accounts must be set-up manually in the database for security purposes.

Admin accounts also have the ability to modify overall risk factors for quotes and policies.


## API Reference

### Customer Endpoints

#### Get All Customers

**URL:** `GET /customer`  
**Description:** Retrieves all customers from the database.  
**Response Example:**
```json
{
  "success": true,
  "message": "All customers retrieved!",
  "object": [...]
}
```

#### Get Customer by ID

**URL:** `GET /customer/{id}`  
**Description:** Retrieves a specific customer by their ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Customer with the ID {id} retrieved!",
  "object": {...}
}
```

#### Get Customers by Name

**URL:** `GET /customer/name`  
**Params:** `firstName`, `lastName`  
**Description:** Retrieves customers matching the given first and last name.  
**Response Example:**
```json
{
  "success": true,
  "message": "All customers named John Doe retrieved!",
  "object": [...]
}
```

#### Get Customers by Email

**URL:** `GET /customer/email`  
**Params:** `email`  
**Description:** Retrieves customers matching the given email.  
**Response Example:**
```json
{
  "success": true,
  "message": "All customers with the email john@example.com retrieved!",
  "object": [...]
}
```

#### Register a New Customer

**URL:** `POST /customer/register`  
**Params:** `firstName`, `lastName`, `birthday`, `email`, `username`, `password`, `addressId`  
**Description:** Registers a new customer with the given details.  
**Response Example:**
```json
{
  "success": true,
  "message": "Customer registration successful!"
}
```

_On conflict (e.g., username exists):_
```json
{
  "success": false,
  "message": "Username already exists. Please choose a new username."
}
```

#### Login Customer

**URL:** `POST /customer/login`  
**Params:** `username`, `password`  
**Description:** Authenticates a customer login.  
**Response Example (Success):**
```json
{
  "message": "Login successful!",
  "customerId": 1,
  "username": "johndoe"
}
```

**Response Example (Failure):**
```json
{
  "success": false,
  "message": "Invalid credentials."
}
```

#### Update Customer by ID

**URL:** `PUT /customer/{id}`  
**Params:** `firstName`, `lastName`, `birthday`, `email`, `addressId`  
**Description:** Updates basic info and address of a customer.  
**Response Example:**
```json
{
  "success": true,
  "message": "Customer with ID 1 updated successfully!"
}
```

#### Reset Customer Password

**URL:** `PUT /customer/reset/{id}`  
**Params:** `oldPassword`, `newPassword`  
**Description:** Resets the customer's password securely.  
**Response Example (Success):**
```json
{
  "success": true,
  "message": "Customer password updated successfully!"
}
```

**Response Example (Invalid credentials):**
```json
{
  "success": false,
  "message": "Invalid credentials."
}
```

#### Delete Customer by ID

**URL:** `DELETE /customer/{id}`  
**Description:** Deletes a customer by their ID.  
**Response Example (Success):**
```json
{
  "success": true,
  "message": "Customer with ID 1 deleted successfully!"
}
```

### Employee Endpoints

#### Get All Employees

**URL:** `GET /employee`  
**Description:** Retrieves all employees from the database.  
**Response Example:**
```json
{
  "success": true,
  "message": "All employees retrieved!",
  "object": [...]
}
```

#### Login Employee

**URL:** `POST /employee/login`  
**Params:** `username`, `password`  
**Description:** Authenticates an employee login.  
**Response Example:**
```json
{
  "message": "Login successful!",
  "object": {...}
}
```

#### Update Employee Name

**URL:** `PUT /employee/{id}`  
**Params:** `firstName`, `lastName`  
**Description:** Updates an employee's name.  
**Response Example:**
```json
{
  "success": true,
  "message": "Employee with ID 1 updated successfully!"
}
```

#### Employee Updates Customer Info

**URL:** `PUT /employee/customer/{id}`  
**Params:** `firstName`, `lastName`, `birthday`, `email`, `addressId`, `username`, `password`  
**Description:** Allows an employee to update a customer’s profile.  
**Response Example:**
```json
{
  "success": true,
  "message": "Customer with ID 1 updated successfully!"
}
```

#### Reset Employee Password

**URL:** `PUT /employee/reset/{id}`  
**Params:** `oldPassword`, `newPassword`  
**Description:** Allows an employee to securely reset their password.  
**Response Example:**
```json
{
  "success": true,
  "message": "Employee password updated successfully!"
}
```

I'll create markdown documentation for the API endpoints based on the provided code. Here's the raw markdown documentation:

### Admin Endpoints
#### Get Risk Factors
**URL:** `GET /admin/risk`  
**Description:** Retrieves the current risk factors configuration used in premium calculations.  
**Response Example:**
```json
{
  "success": true,
  "message": "All risk factors retrieved!",
  "object": {}
}
```

#### Register Employee
**URL:** `POST /admin/register`  
**Description:** Registers a new employee in the system. Username must be unique.  
**Parameters:**
- `firstName` - The employee's first name
- `lastName` - The employee's last name
- `email` - The employee's email address
- `username` - The employee's username (must be unique)
- `password` - The plain-text password to be encrypted

**Response Example:**
```json
{
  "success": true,
  "message": "Employee registration successful!"
}
```

#### Update Employee
**URL:** `PUT /admin/{id}`  
**Description:** Updates information for an existing employee by ID.  
**Parameters:**
- `employeeId` - The ID of the employee to update
- `firstName` - The updated first name
- `lastName` - The updated last name
- `email` - The updated email address
- `username` - The updated username
- `password` - The new password (to be encrypted)
- `admin` - Whether the employee should be an admin

**Response Example:**
```json
{
  "success": true,
  "message": "Employee with ID {id} updated successfully!"
}
```

#### Update Risk Factors
**URL:** `PUT /admin/risk`  
**Description:** Updates risk factors used in quote and policy premium calculations.  
**Request Body:** RiskFactors object  
**Response Example:**
```json
{
  "success": true,
  "message": "Config File 'risk_factor_config.xml' updated!"
}
```

#### Delete Employee
**URL:** `DELETE /admin/{id}`  
**Description:** Deletes an employee from the database by their unique identifier.  
**Response Example:**
```json
{
  "success": true,
  "message": "Employee with ID {id} deleted successfully!"
}
```

### Home Endpoints
#### Get All Homes
**URL:** `GET /home`  
**Description:** Retrieves all homes stored in the system.  
**Response Example:**
```json
{
  "success": true,
  "message": "All homes retrieved!",
  "object": []
}
```

#### Get Customer Homes
**URL:** `GET /home/{id}`  
**Description:** Retrieves all homes associated with a specific customer ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "All homes of customer ID {id} retrieved!",
  "object": []
}
```

#### Create Home
**URL:** `POST /home/{id}/{additional_id}`  
**Description:** Creates a new home linked to an address ID and customer ID.  
**Parameters:**
- `addressId` - The ID of the address (path variable)
- `customerId` - The ID of the customer (path variable)
- `dateBuilt` - The date the home was built
- `homeValue` - The value of the home
- `heatingType` - The type of heating
- `location` - The home's location (urban/rural)
- `dwellingType` - The dwelling type

**Response Example:**
```json
{
  "success": true,
  "message": "Home created successfully!",
  "object": {}
}
```

#### Update Home
**URL:** `PUT /home/{id}`  
**Description:** Updates an existing home's information.  
**Parameters:**
- `homeId` - The ID of the home to update (path variable)
- `dateBuilt` - The updated date built
- `homeValue` - The updated home value
- `heatingType` - The updated heating type
- `location` - The updated location
- `dwellingType` - The updated dwelling type
- `customerId` - The ID of the new/updated customer
- `addressId` - The ID of the new/updated address

**Response Example:**
```json
{
  "success": true,
  "message": "Home with ID {id} updated successfully!"
}
```

#### Delete Home
**URL:** `DELETE /home/{id}`  
**Description:** Deletes a specific home by ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Home with ID {id} deleted successfully!"
}
```

### Address Endpoints
#### Get All Addresses
**URL:** `GET /address`  
**Description:** Retrieves all addresses stored in the system.  
**Response Example:**
```json
{
  "success": true,
  "message": "All addresses retrieved!",
  "object": []
}
```

#### Get Address By ID
**URL:** `GET /address/{id}`  
**Description:** Retrieves an address by its ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Address with ID {id} retrieved!",
  "object": {}
}
```

#### Create Address
**URL:** `POST /address`  
**Description:** Creates a new address or returns an existing one if duplicate found.  
**Parameters:**
- `unit` - The unit number
- `street` - The street name
- `city` - The city name
- `province` - The province
- `postalCode` - The postal code

**Response Example:**
```json
{
  "success": true,
  "message": "Address created successfully!",
  "object": {}
}
```

#### Update Address
**URL:** `PUT /address/{id}`  
**Description:** Updates an address by its ID.  
**Parameters:**
- `addressId` - The ID of the address to update (path variable)
- `unit` - The new unit number
- `street` - The new street name
- `city` - The new city
- `province` - The new province
- `postalCode` - The new postal code

**Response Example:**
```json
{
  "success": true,
  "message": "Address with ID {id} updated successfully!"
}
```

#### Delete Address
**URL:** `DELETE /address/{id}`  
**Description:** Deletes an address by its ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Address with ID {id} deleted successfully!"
}
```

Here's the markdown documentation for the Auto and Accident endpoints based on the provided code:

### Auto Endpoints
#### Get All Autos
**URL:** `GET /auto`  
**Description:** Retrieves all autos stored in the system.  
**Response Example:**
```json
{
  "success": true,
  "message": "All autos retrieved!",
  "object": []
}
```

#### Get Customer Autos
**URL:** `GET /auto/{id}`  
**Description:** Retrieves all auto objects associated with a specific customer ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "All autos with customer ID {id} retrieved!",
  "object": []
}
```

#### Create Auto
**URL:** `POST /auto/{id}`  
**Description:** Creates a new auto record linked to a customer.  
**Parameters:**
- `customerId` - The ID of the customer (path variable)
- `make` - The vehicle make (e.g., Toyota)
- `model` - The vehicle model (e.g., Corolla)
- `year` - The vehicle year

**Response Example:**
```json
{
  "success": true,
  "message": "Auto created successfully!"
}
```

#### Update Auto
**URL:** `PUT /auto/{id}`  
**Description:** Updates an auto record by its ID.  
**Parameters:**
- `autoId` - The ID of the auto to update (path variable)
- `make` - The updated make
- `model` - The updated model
- `year` - The updated year

**Response Example:**
```json
{
  "success": true,
  "message": "Auto with ID {id} updated successfully."
}
```

#### Delete Auto
**URL:** `DELETE /auto/{id}`  
**Description:** Deletes a specific auto object by ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Auto with ID {id} deleted successfully."
}
```

### Accident Endpoints
#### Get All Accidents
**URL:** `GET /accident`  
**Description:** Retrieves all accident records in the system.  
**Response Example:**
```json
{
  "success": true,
  "message": "All accidents retrieved!",
  "object": []
}
```

#### Get Accident By ID
**URL:** `GET /accident/{id}`  
**Description:** Retrieves a specific accident by its ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Accident with ID {id} retrieved!",
  "object": {}
}
```

#### Create Accident
**URL:** `POST /accident/{id}`  
**Description:** Creates a new accident record for a specific customer.  
**Parameters:**
- `customerId` - The ID of the customer involved (path variable)
- `dateOfAccident` - The date the accident occurred

**Response Example:**
```json
{
  "success": true,
  "message": "Accident created successfully!"
}
```

#### Update Accident
**URL:** `PUT /accident/{id}`  
**Description:** Updates the date of an existing accident.  
**Parameters:**
- `accidentId` - The ID of the accident to update (path variable)
- `dateOfAccident` - The new date of the accident

**Response Example:**
```json
{
  "success": true,
  "message": "Accident with ID {id} updated successfully!"
}
```

#### Delete Accident
**URL:** `DELETE /accident/{id}`  
**Description:** Deletes an accident record by its ID.  
**Response Example:**
```json
{
  "success": true,
  "message": "Accident with ID {id} deleted successfully!"
}
```

### Home Quote Endpoints

#### Get All Home Quotes
**URL:** `GET /home-quote`
**Description:** Retrieves all home insurance quotes from the database.
**Response Example:**
```json
{
  "success": true,
  "message": "All home quotes retrieved!",
  "object": [...]
}
```

#### Get Home Quote By ID
**URL:** `GET /home-quote/{id}`
**Description:** Retrieves a home quote by its ID.
**Response Example:**
```json
{
  "success": true,
  "message": "Home quote with ID {id} retrieved!",
  "object": {...}
}
```

#### Get Home Quotes By Customer ID
**URL:** `GET /home-quote/customer/{id}`
**Description:** Retrieves all home quotes for a specific customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All home quotes with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Get Active Home Quotes By Customer ID
**URL:** `GET /home-quote/active/{id}`
**Description:** Retrieves only active home quotes for a specific customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All active home quotes with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Create Home Quote
**URL:** `POST /home-quote/{customerId}/{homeId}`
**Description:** Creates a new home insurance quote based on customer and home information.
**Parameters:**
- `liability` (int): The liability limit (e.g., 1000000 or 2000000)
- `packagedQuote` (boolean): True if bundled with an auto policy
**Response Example:**
```json
{
  "success": true,
  "message": "Home Quote created successfully!",
  "object": {...}
}
```

#### Update Home Quote
**URL:** `PUT /home-quote/{id}`
**Description:** Sets the active status of a home quote.
**Parameters:**
- `activeStatus` (boolean): True to activate, false to deactivate
**Response Example:**
```json
{
  "success": true,
  "message": "Home Quote with ID {id} updated successfully!",
  "object": {...}
}
```

### Auto Quote Endpoints

#### Get All Auto Quotes
**URL:** `GET /auto-quote`
**Description:** Retrieves all auto insurance quotes in the system.
**Response Example:**
```json
{
  "success": true,
  "message": "All auto quotes retrieved",
  "object": [...]
}
```

#### Get Auto Quote By ID
**URL:** `GET /auto-quote/{id}`
**Description:** Retrieves a specific auto quote by ID.
**Response Example:**
```json
{
  "success": true,
  "message": "Auto quote with ID {id} retrieved!",
  "object": {...}
}
```

#### Get Auto Quotes By Customer ID
**URL:** `GET /auto-quote/customer/{id}`
**Description:** Retrieves all auto quotes for a specific customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All auto quotes with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Get Active Auto Quotes By Customer ID
**URL:** `GET /auto-quote/active/{id}`
**Description:** Retrieves all active auto quotes for a specific customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All active auto quotes with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Create Auto Quote
**URL:** `POST /auto-quote/{customerId}/{autoId}`
**Description:** Creates a new auto insurance quote for a customer and vehicle.
**Parameters:**
- `packagedQuote` (boolean): True if the customer is bundling with a home policy
**Response Example:**
```json
{
  "success": true,
  "message": "Auto Quote created successfully!",
  "object": {...}
}
```

#### Update Auto Quote
**URL:** `PUT /auto-quote/{id}`
**Description:** Updates the active status of an auto quote.
**Parameters:**
- `activeStatus` (boolean): True to activate, false to deactivate
**Response Example:**
```json
{
  "success": true,
  "message": "Auto Quote with ID {id} updated successfully!",
  "object": {...}
}
```

### Home Policy Endpoints

#### Get All Home Policies
**URL:** `GET /home-policy`
**Description:** Retrieves all home insurance policies in the system.
**Response Example:**
```json
{
  "success": true,
  "message": "All home policies retrieved!",
  "object": [...]
}
```

#### Get Home Policy By ID
**URL:** `GET /home-policy/{id}`
**Description:** Retrieves a specific home policy by its ID.
**Response Example:**
```json
{
  "success": true,
  "message": "Home policy with ID {id} retrieved.",
  "object": {...}
}
```

#### Get Home Policies By Customer ID
**URL:** `GET /home-policy/customer/{id}`
**Description:** Retrieves all home policies for a specific customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All home policies with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Get Active Home Policies By Customer ID
**URL:** `GET /home-policy/active/{id}`
**Description:** Retrieves only active home policies for a customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All active home policies with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Create Home Policy
**URL:** `POST /home-policy/{quoteId}`
**Description:** Creates a new home insurance policy based on an existing quote.
**Parameters:**
- `effectiveDate` (LocalDate): The start date of the policy
**Response Example:**
```json
{
  "success": true,
  "message": "Home Policy created successfully!",
  "object": {...}
}
```

#### Update Home Policy
**URL:** `PUT /home-policy/{id}`
**Description:** Updates the end date and active status of a home policy.
**Parameters:**
- `activeStatus` (boolean): True to activate, false to deactivate
- `endDate` (LocalDate): The updated end date
**Response Example:**
```json
{
  "success": true,
  "message": "Home Policy with ID {id} updated successfully!",
  "object": {...}
}
```

### Auto Policy Endpoints

#### Get All Auto Policies
**URL:** `GET /auto-policy`
**Description:** Retrieves all auto insurance policies.
**Response Example:**
```json
{
  "success": true,
  "message": "All auto policies retrieved!",
  "object": [...]
}
```

#### Get Auto Policy By ID
**URL:** `GET /auto-policy/{id}`
**Description:** Retrieves a specific auto policy by ID.
**Response Example:**
```json
{
  "success": true,
  "message": "Auto policy with ID {id} retrieved!",
  "object": {...}
}
```

#### Get Auto Policies By Customer ID
**URL:** `GET /auto-policy/customer/{id}`
**Description:** Retrieves all auto policies for a customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All auto policies with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Get Active Auto Policies By Customer ID
**URL:** `GET /auto-policy/active/{id}`
**Description:** Retrieves all active auto policies for a customer.
**Response Example:**
```json
{
  "success": true,
  "message": "All active auto policies with customer ID {id} retrieved!",
  "object": [...]
}
```

#### Create Auto Policy
**URL:** `POST /auto-policy/{quoteId}`
**Description:** Creates a new auto insurance policy based on an existing auto quote.
**Parameters:**
- `effectiveDate` (LocalDate): The start date of the policy
**Response Example:**
```json
{
  "success": true,
  "message": "Auto Policy created successfully!",
  "object": {...}
}
```

#### Update Auto Policy
**URL:** `PUT /auto-policy/{id}`
**Description:** Updates an auto policy's active status and end date.
**Parameters:**
- `activeStatus` (boolean): True to activate, false to deactivate
- `endDate` (LocalDate): The updated end date
**Response Example:**
```json
{
  "success": true,
  "message": "Auto Policy with ID {id} updated successfully!",
  "object": {...}
}
```

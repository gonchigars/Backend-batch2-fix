### Application Components and Their Roles

1. **Model (`Greeting.java`)**
2. **Repository (`GreetingRepository.java`)**
3. **Service (`GreetingService.java`)**
4. **Controller (`GreetingController.java`)**
5. **Global Exception Handler (`GlobalExceptionHandler.java`)**

### 1. Model (`Greeting.java`)

The `Greeting` class is a JPA entity representing the `greeting` data in the database.

- **Annotations**:
  - `@Entity`: Marks the class as a JPA entity.
  - `@Id` and `@GeneratedValue`: Indicate the primary key and its generation strategy.
  - `@NotBlank` and `@Size`: Validation constraints for the `name` field.

### 2. Repository (`GreetingRepository.java`)

The `GreetingRepository` interface extends `JpaRepository`, providing CRUD operations and a custom method.

- **JpaRepository**: Automatically provides implementations for standard CRUD operations.
- **Custom Method**: `Optional<Greeting> findByName(String name)`: A query method to find a greeting by its name.

### 3. Service (`GreetingService.java`)

The `GreetingService` class contains business logic for managing greetings.

- **Autowired Repository**: `GreetingRepository` is injected to interact with the database.
- **Methods**:
  - `saveGreeting(String name)`: Saves a new greeting, ensuring the name is not empty.
  - `getGreetingByName(String name)`: Finds a greeting by its name.
  - `getAllGreetings()`: Retrieves all greetings.
  - `getGreetingById(Long id)`: Finds a greeting by its ID.
  - `updateGreeting(Long id, Greeting greetingDetails)`: Updates an existing greeting.
  - `deleteGreeting(Long id)`: Deletes a greeting by its ID.

### 4. Controller (`GreetingController.java`)

The `GreetingController` class handles HTTP requests and maps them to service methods.

- **Annotations**:
  - `@RestController`: Marks the class as a REST controller.
  - `@RequestMapping`: Sets the base URL for all endpoints in this controller.
- **Endpoints**:
  - `@PostMapping`: Creates a new greeting.
  - `@GetMapping`: Retrieves all greetings or a specific greeting by ID or name.
  - `@PutMapping`: Updates an existing greeting.
  - `@DeleteMapping`: Deletes a greeting by ID.

### 5. Global Exception Handler (`GlobalExceptionHandler.java`)

The `GlobalExceptionHandler` class handles exceptions thrown by the application.

- **Annotations**:
  - `@RestControllerAdvice`: Indicates this class provides advice for all controllers.
  - `@ExceptionHandler`: Specifies the type of exception to handle.
- **Method**:
  - `handleGreetingException(GreetingException ex)`: Handles `GreetingException` and returns an appropriate HTTP response.

### How It Works Together

1. **Creating a Greeting**:

   - **Request**: POST `/api/v1/greetings` with a JSON body.
   - **Controller**: `createGreeting` method is called.
   - **Service**: `saveGreeting` method validates and saves the greeting.
   - **Repository**: `save` method persists the greeting to the database.
   - **Response**: Returns the created greeting with `HTTP 201 CREATED`.

2. **Retrieving All Greetings**:

   - **Request**: GET `/api/v1/greetings`.
   - **Controller**: `getAllGreetings` method is called.
   - **Service**: `getAllGreetings` method retrieves all greetings from the repository.
   - **Response**: Returns a list of greetings with `HTTP 200 OK`.

3. **Retrieving a Greeting by Name**:

   - **Request**: GET `/api/v1/greetings/search?name=John`.
   - **Controller**: `getGreetingByName` method is called.
   - **Service**: `getGreetingByName` method calls `findByName` on the repository.
   - **Repository**: `findByName` method retrieves the greeting.
   - **Response**: Returns the greeting with `HTTP 200 OK` or `HTTP 404 NOT FOUND` if not found.

4. **Updating a Greeting**:

   - **Request**: PUT `/api/v1/greetings/{id}` with a JSON body.
   - **Controller**: `updateGreeting` method is called.
   - **Service**: `updateGreeting` method validates and updates the greeting.
   - **Repository**: `save` method updates the greeting in the database.
   - **Response**: Returns the updated greeting with `HTTP 200 OK` or `HTTP 404 NOT FOUND` if not found.

5. **Deleting a Greeting**:

   - **Request**: DELETE `/api/v1/greetings/{id}`.
   - **Controller**: `deleteGreeting` method is called.
   - **Service**: `deleteGreeting` method deletes the greeting.
   - **Repository**: `delete` method removes the greeting from the database.
   - **Response**: Returns `HTTP 204 NO CONTENT` or `HTTP 404 NOT FOUND` if not found.

6. **Error Handling**:
   - **Exception Thrown**: If a validation fails, a `GreetingException` is thrown.
   - **GlobalExceptionHandler**: Catches `GreetingException` and returns a `HTTP 400 BAD REQUEST` with the error message.

### Example Workflow

1. **Client sends a GET request** to `/api/v1/greetings/search?name=John`.
2. **Controller's `getGreetingByName` method** is called.
3. **Service's `getGreetingByName` method** calls the repository to find the greeting.
4. **Repository's `findByName` method** returns an `Optional<Greeting>`.
5. **Controller checks the Optional** and returns the greeting or a `404 NOT FOUND` response.
6. If the name is empty, **`GreetingException` is thrown**.
7. **GlobalExceptionHandler catches the exception** and returns a `400 BAD REQUEST` with the error message.

This structured approach ensures your application is robust, with clear separation of concerns, efficient data handling, and comprehensive error management.

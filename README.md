# API Rest Portfolio Project

This project is a RESTful API built with Java 22, Spring Boot 3.3.0, JPA, Docker, MongoDB, PostgreSQL, and JWT. It is designed to showcase a portfolio application managing four main entities: Order, User, Inventory, and Item. The project uses Maven for dependency management, with Lombok to reduce boilerplate code.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Dependencies](#dependencies)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Configuration](#configuration)
- [Security](#security)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contact](#contact)


## Technologies Used

- **Java 22**: Latest version of Java for optimal performance and features.
- **Spring Boot 3.3.0**: Simplifies the development of production-ready applications.
- **JPA (Java Persistence API)**: For ORM (Object Relational Mapping).
- **MongoDB**: NoSQL database for storing non-relational data.
- **PostgreSQL**: Relational database for storing structured data.
- **JWT (JSON Web Tokens)**: For securing the API.
- **Docker**: For containerizing the application.
- **Maven**: For dependency management.
- **Lombok**: To reduce boilerplate code in Java.
- **Spring Boot Starter Data JPA**: Enables Java Persistence API (JPA) support for database operations.
- **Spring Boot Starter Web**: Provides web development features, including RESTful API support.
- **Postgres**: The PostgreSQL database driver for connecting to a PostgreSQL database.
- **Spring Boot Starter Data MongoDB**: Enables MongoDB support for NoSQL database operations.
- **Spring Boot Starter Test**: Provides testing support for Spring Boot applications.
- **Spring Security Core**: The core security module for Spring, providing authentication and authorization features.
- **Spring Boot Starter Security**: Provides security features, including authentication and authorization, for Spring Boot applications.
- **JWT API, Impl, and Jackson**: Libraries for working with JSON Web Tokens (JWT) for authentication and authorization, including API, implementation, and Jackson serialization support.

## Dependencies

- **spring-boot-starter-data-jpa**: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
- **spring-boot-starter-web**: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
- **postgresql**: https://mvnrepository.com/artifact/org.postgresql/postgresql
- **spring-boot-starter-data-mongodb**: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-mongodb
- **spring-boot-starter-test**: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
- **lombok**: https://mvnrepository.com/artifact/org.projectlombok/lombok
- **spring-security-core**: https://mvnrepository.com/artifact/org.springframework.security/spring-security-core
- **spring-boot-starter-security**: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
- **jjwt-api**: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
- **jjwt-impl**: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
- **jjwt-jackson**: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson


## Setup and Installation

1. Clone the repository:
    ```shell
    git clone https://github.com/Sanchez1004/Inventory-Java-API-REST.git
    cd Inventory-Java-API-REST
    ```
3. Build the Docker image:
    ````shell
   docker-compose up -d
   ````
3. Build the project using Maven:
    ````shell
    mvn clean install
    ````

4. Run the application:
    ````shell
    mvn spring-boot:run
    ````

## Running the apllication

The application runs locally and uses Docker Compose for the PostgreSQL database.
Running Locally

Ensure you have PostgreSQL running via Docker Compose. Update the application properties accordingly if needed.
Running with Docker Compose
   
### Running With Docker Compose
1. Start the PostgreSQL service:
   ```shell
   docker-compose up -d
   ````   
2. Build and run the application:
   ````shell
   mvn clean install
   mvn spring-boot:run
   ````

## Configuration

Create a .env file in the root directory of your project based on the provided .env.template. This file should contain your database credentials and other configuration settings.

````properties
# POSTGRE
SPRING_DATASOURCE_POSTGRES_URL=jdbc:postgresql://localhost:(port)/(dbname)
SPRING_DATASOURCE_POSTGRES_USERNAME=
SPRING_DATASOURCE_POSTGRES_PASSWORD=
SPRING_DATASOURCE_POSTGRES_DB=

# MONGODB
SPRING_DATASOURCE_MONGO_USERNAME=
SPRING_DATASOURCE_MONGO_PASSWORD=
SPRING_DATASOURCE_MONGO_DB=
SPRING_DATASOURCE_MONGO_CLUSTER=

SECRET_KEY=
````
## Environment Variables

The project uses environment variables to configure the database connections. You need to set the following variables in your .env file:

    SPRING_DATASOURCE_POSTGRES_URL: the URL of the Postgres database
    SPRING_DATASOURCE_POSTGRES_USERNAME: the username for the Postgres database
    SPRING_DATASOURCE_POSTGRES_PASSWORD: the password for the Postgres database
    SPRING_DATASOURCE_POSTGRES_DB: the name of the Postgres database
    SPRING_DATASOURCE_MONGO_USERNAME: the username for the MongoDB database
    SPRING_DATASOURCE_MONGO_PASSWORD: the password for the MongoDB database
    SPRING_DATASOURCE_MONGO_DB: the name of the MongoDB database
    SPRING_DATASOURCE_MONGO_CLUSTER: the cluster name for the MongoDB database
    SECRET_KEY: the secret key for JWT authentication

##### The project uses a docker-compose.yml file to configure the Docker environment. The file sets up a Postgres service with a container name, environment variables, and port mapping. It also mounts volumes for the Postgres data and configuration files.

The postgresql.conf file sets the listen_addresses to '*' to allow local connections.
The pg_hba.conf file configures the Postgres authentication.
The .env.template file provides a template for environment variables, including Postgres and MongoDB connection settings and a secret key.


## Security
The project uses JWT for authentication and authorization. The JWT API, Impl, and Jackson dependencies are used to generate and verify JSON Web Tokens.
To add security to the API:

The API uses JWT (JSON Web Tokens) for authentication and authorization. Ensure you include the JWT token in the Authorization header of your requests:
   ````shell
    Authorization: Bearer <token>
   ````

You can modify the docker-compose.yml file to accept connections from other sites by updating the ports section. For example:
docker-compose.yml:

      ports:
      - "5432:5432"
      - "8080:8080" 

This will map port 8080 on the host machine to port 8080 in the container, allowing external connections to the API.
You can also add additional security measures, such as SSL/TLS encryption, to the API.

## API Endpoints 
- InventoryController:
   ````http request
    POST - /inventories/admin/create - Creates an item in the inventory.
    GET - /inventories - Finds items in the inventory by name containing the specified string.
    PUT - /inventories/admin/add-stock/{id} - Adds stock to an item in the inventory by its ID.
   
- ItemController:
   ````http request
    GET - /items/user/get-items - Retrieves all items.
    GET - /items/admin/list-of-items - Retrieves the list of names of all items.
    GET - /items/user/get-item/{id} - Retrieves an item by its ID.
    POST - /items/admin/create-item - Creates a new item.
    PUT - /items/admin/update-item/{id} - Updates an existing item.
    DELETE - /items/admin/delete-item/{id} - Deletes an item by its ID.

- OrderController:
   ````http request
    GET - /orders/admin/list-orders - Retrieves a list of all orders.
    GET - /orders/admin/find-order/{id} - Retrieves an order by its ID.
    GET - /orders/admin/find-order-by-client-name/{clientName} - Retrieves orders by the client's name.
    POST - /orders/admin/create-order - Creates a new order.
    PUT - /orders/admin/update-order-items - Adds items to an existing order by its ID.

- AuthController:
   ````http request
    POST - /auth/login - Logs in.
    POST - /auth/register - Registers.

- UserController:
   ````http request
    GET - /users/admin/list-users - Retrieves a list of all users.
    GET - /users/admin/search-user-by-id/{id} - Retrieves a user by their ID.
    GET - /users/admin/search-user-by-email/{email} - Retrieves a user by their email.
    PUT - /users/admin/update-users/{id} - Updates a user's information.

- DemoController:
   ````http request
    POST - /api/v1/demo - Welcome from secure endpoint.

## Testing

The project includes unit tests and integration tests using Spring Boot's testing framework. You can run the tests using Maven or your preferred testing tool.
- Example using maven to run the tests:
````shell
   mvn test
````

## Contact

- Mail: [César Daniel Ocampo Sánchez](mailto:dev.cesar1004@gmail.com)

- Github: [Sanchez1004](https://github.com/Sanchez1004)

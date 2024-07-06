# Tool Rental Application

This repository contains a tool rental application. The application simulates a store similar to Home Depot where users can rent tools.

## Features

- **Tool Management:** Add and list tools, tool brands, and tool types.
- **Rental Management:** Create and manage rentals.
- **API Endpoints:** Utilize RESTful APIs for interacting with tools and rentals.

## Technologies Used

- Java
- Spring Boot
- Hibernate
- MySQL
- Flyway
- Swagger

## Getting Started

To get a local copy up and running, follow these steps:

### Prerequisites

- Java SDK
- Maven
- MySQL

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/kockasfulu/ns0724
   ```
2. Navigate into the project directory:
   ```sh
   cd ns-0724
   ```
   
### Environments
The `.env` files has the environment variables needed to configure your local MySQL database. 
There are environments for both the dev and the test environments. Comment or uncomment the
variables and values depending on which environment you want to run.
You can change these environment variables, but then you will have to 
update the `application.properties` files, too.

### Running the database

Use `docker compose` to start mysql in a docker container for the application.

#### Dev environment
Make sure that you have the following in your `.env` file:
```sh
MYSQL_DATABASE=tooltrek_db
MYSQL_USER=tooltrek
MYSQL_PASSWORD=szerszam
MYSQL_ROOT_PASSWORD=root
MYSQL_PORT=3306
```
Start the container:
```sh
docker compose up -d tooltrek-db
```
#### Test environment
Make sure that you have the following in your `.env` file:
```sh
MYSQL_DATABASE=tooltrek_db_test
MYSQL_USER=tooltrek_test
MYSQL_PASSWORD=SZerszam78
MYSQL_ROOT_PASSWORD=root
MYSQL_PORT=3306
```
Start the container:
```sh
docker compose up -d tooltrek-db-test
```

### Running the Application

1. Build the project with Maven:
    ```sh
    mvn clean package
    ```
2. Run the application:
    ```sh
    mvn spring-boot:run
    ```
### Testing
MySQL test database should be up and running.
Unit and integration tests are included. Run tests with Maven:
```sh
mvn test
```

### API Documentation
Explore and test the APIs using Swagger UI: http://localhost:8080/swagger-ui.html


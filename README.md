<h1>World-DB-Spring-MVC</h1>
World-DB-Spring-MVC team consisting of Howard, Patryk S, Selam, Mamoon, Alastair, and Phoenix 👋.

## Project Overview
This project creates a Java applicaiton that uses an SQL database which contains a list of countries and cities, along with other details.
This applicaiton allows users to query certain fields within the database, while following the spring architecture layout: Entities->Repository->Service->Controller
We implement RestAPIs and endpoints to allow the following CRUD methods.

- POST /city: Adds a new city.
- GET /cities: Retrieves all cities.
- GET /city/{id}: Retrieves a city by its ID.
- GET /city/name: Retrieves cities by their name.
- PUT /city/{id}: Updates an existing city.
- DELETE /city/{id}: Deletes a city by its ID.
- The above is similar for the countries controller

## Acceptance Criteria
- Interact with the MySQL World Database
- Use Spring JPA to connect and communicate with the Database
- Use basic CRUD operations
- Provide multiple types of search methods
- Implement the service layer in your application
- Tested with WebMVCTests
- GUI to be provided via swaggar
- Secure endpoints wiht an API
- Error handling of API endpoints


## Dependencies
- JDK 21
- JUnit
- Mockito
- SpringBoot
- Spring Reactive Web
- Rest Repositories
- MariaDB Driver
- Spring HATEOS
- Thymeleaf
- JDBC API
- Spring Data JPA
- MySQL Driver
- Validation


<h2>Connecting to your database</h2>


To connect to your database please fill out the following in your application.properties file. In addition, ensure that you have the World databse on your local computer.
```
spring.datasource.url=jdbc:mysql://localhost:3306/world
spring.datasource.username=<YOUR USERNAME>
spring.datasource.password=<YOUR PASSWORD>
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

```

## How to use the Program 
Open the program and run the main method.

Open up browser and access your localhost
```
http://localhost:8080/
```
From here you can access all our endpoints.


##  

📫 If you encounter any bugs, please open up an issue to let us know.
Alternatively, we welcome suggestions for any updates or improvements you would like to see! 

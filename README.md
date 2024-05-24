<h1>World-DB-Spring-MVC</h1>
World-DB-Spring-MVC team consisting of Howard, Patryk S, Selam, Mamoon, Alastair, and Phoenix 👋.

## Project Overview
TThis project creates a Java application that uses an SQL database containing a list of countries and cities, along with other details. This application allows users to query certain fields within the database, following the Spring architecture layout: Entities -> Repository -> Service -> Controller. We have implemented REST APIs and endpoints to allow the following CRUD methods while providing each page with a stylized and user-friendly design.

In addition, we have added a frontend user interface using Thymeleaf and implemented user login with Spring Security. This enhancement allows users to interact with the application through a web interface, providing a seamless and secure user experience. The login functionality ensures that only authenticated users can access certain features, maintaining the integrity and security of the application.

- POST /city: Adds a new city.
- GET /cities: Retrieves all cities.
- GET /city/{id}: Retrieves a city by its ID.
- GET /city/name: Retrieves cities by their name.
- PUT /city/{id}: Updates an existing city.
- DELETE /city/{id}: Deletes a city by its ID.
- The above is similar for the countries controller

## Acceptance Criteria
- Interact with the MySQL World Database
- Application allows user to view,add, update and delete from the 3 SQL tables
- Use Tailwind to develop the frontend of the site
- Use Spring security to secure the application.
- Tested with WebMVCTests and MockMVCTests


## Dependencies
- JDK 21
  

<h2>Connecting to your database</h2>


To connect to your database please fill out the following in your application.properties file. In addition, ensure that you have the World databse on your local computer.
```
spring.datasource.url=jdbc:mysql://localhost:3306/world
spring.datasource.username=<YOUR USERNAME>
spring.datasource.password=<YOUR PASSWORD>
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

```

In addition to ensuring your database is correctly connected to, please make sure to run this script to ensure you have the user tables.

```
create table if not exists users
(
    id        bigint       not null
        primary key,
    password  varchar(255) null,
    roles     varchar(255) null,
    user_name varchar(255) null
);

create table if not exists users_seq
(
    next_val bigint null
);

insert into users_seq (next_val) values (1);
```

## How to use the Program 
Open the program and run the main method.

Open up browser and access your localhost
```
http://localhost:8080/
```
From here you can access all our endpoints from the user interface.


##  

📫 If you encounter any bugs, please open up an issue to let us know.
Alternatively, we welcome suggestions for any updates or improvements you would like to see! 

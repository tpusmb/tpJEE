# Centralize

This repo implement a spring boot website to manage all the companie robot.
For customer manage card listing etc

## Setup

### Installation mysql server

To run this project you will need a Mysql server. For window you can install `WAMP`

### General setup

Copy the file `application.properties.example` and replace the name by `application.properties`

In the `application.properties` edit this tow var 

    spring.datasource.username = username 
    spring.datasource.password = pass

Put the username and password for mysql connection

## Build and run

To build `mvn clean install`
To run `mvn spring-boot:run`

The default [link](http://localhost:9090)
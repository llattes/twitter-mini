# twitter-mini
Mini messaging service, inspired by Twitter

## Prerequisites

In order to run this application locally you need to check if you have the following installed:

- Java 1.8 *[Java(TM) SE Runtime Environment (build 1.8.0_111-b14)]*
- Apache Maven 3.x *[Apache Maven 3.3.3 (7994120775791599e205a5524ec3e0dfe41d4a06; 2015-04-22T08:57:37-03:00)]*
- MySQL 5.7 *[Server version: 5.7.16 MySQL Community Server (GPL)]*
- Git 2.4.5

**Note:** The details between brackets correspond to the versions used during development.

## Running locally

After validating the prerequisites, perform the following steps to run the application locally:

1. Clone the repository into your preferred location: `git clone https://github.com/llattes/twitter-mini.git`
2. Log into your MySQL server (you can use `mysql -u root -p` and enter your password afterwards) and create a database as follows: `CREATE DATABASE twittermini DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;`
3. Review (and update) the database settings in the `application.properties` file located in the project's `src/main/resources` directory. You will probably need to change at least username and password. 
4. Run `mvn clean install` in the directory in which the project was cloned. There should be a `pom.xml` file in that location.
5. `mvn clean install` will run unit tests for both *RestController*s. You can check coverage data opening the following HTML page located in the `/target` folder: `target/site/jacoco/index.html`.  
6. *[Optional]* If you are using Eclipse IDE, run `mvn eclipse:eclipse` to generate the Eclipse metadata. After that task is successful you'll be able to import the *existing project into workspace*.
7. Run `mvn jetty:run-war` or `mvn jetty:run` to start the application. Once you see the following two lines, you should be able to start using the application: `[INFO] Started @5575ms - [INFO] Started Jetty Server`
8. Hit `http://localhost:8080/` to see the list of endpoints of the **twitter-mini** RESTful API and a brief description of how those endpoints work.

![Swagger UI](https://github.com/llattes/twitter-mini/raw/master/readme-files/swagger-ui-screen.png)

## Using the API

Every time the application starts, the database is conveniently seeded with some dummy data.

All the endpoints are secured with HTTP Basic Authentication and since a *stateless* session creation policy was chosen for the application, re-authentication is necessary for each request.

The following is the list of seeded users (during application startup) that can be used to interact with the RESTful API:

Username | Password
-------- | --------
luciano      | luciano
thousandeyes | thousandeyes
twitter      | twitter
johndoe      | johndoe
iamdevloper  | iamdevloper

The following is a Postman screenshot showing how to perform a `GET` request to obtain the tweets of the user authenticated with Basic Auth:

![Postman Basic Auth](https://github.com/llattes/twitter-mini/raw/master/readme-files/postman-screen.png)

All requests can produce `application/json` **and** `application/xml` responses; just add the `Accept` request header with the corresponding media type.

## Deployed application

The application was deployed in an EC2 instance in order to make it available for testing. Please hit http://ec2-35-165-14-112.us-west-2.compute.amazonaws.com:8080 to see the API docs.

Trying a `GET` http://ec2-35-165-14-112.us-west-2.compute.amazonaws.com:8080/api/tweets using the credentials exposed above is a good place to start for testing the API itself.

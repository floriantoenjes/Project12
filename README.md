## Project 12 - Recipe Site

Create a Java web application for adding, editing and searching recipes. Using the MVC pattern with Spring, add user authentication, a favoriting mechanism, a search function and a REST API with authentication. Wire the application to a database with Hibernate, and include unit test coverage.

### Note
The database server has to be started before using the application or running tests, use 
"java -cp .\h2-1.4.192.jar org.h2.tools.Server"
command in project folder to start the server.

The already existing user has the username "user" and the password "password".

The REST API is handled by Spring Data Rest, so it works without using the @RestController annotation. 
It is reachable under "localhost:8080/api/v1" with basic authentication.
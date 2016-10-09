# Project 12 - Recipe Site

The database server has to be started before using the application or running tests, use 
"java -cp .\h2-1.4.192.jar org.h2.tools.Server"
command in project folder to start the server.

The already existing user has the username "user" and the password "password".

The REST API is handled by Spring Data Rest, so it works without using the @RestController annotation. 
It is reachable under "localhost:8080/api/v1" with basic authentication.
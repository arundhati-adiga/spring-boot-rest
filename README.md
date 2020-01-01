# spring-boot-rest
Rest API using spring boot 

# Technologies

– Java 1.8

– Maven 3.6.2

– Spring Boot: 2.2.2.RELEASE

-Junits 5

# Run the project using executable war file

1. Executable war is available at /transaction-category-service-1.0.0-SNAPSHOT.war in project repository

2.Execute from command line , java -jar transaction-category-service-1.0.0-SNAPSHOT.war

3.Tomcat is embedded with war.

4. Swagger UI has been integrated with Spring boot , Swagger UI can be accessed using below url.
       http://localhost:8082/swagger-ui.html
       
5. API docs can be accessed using http://localhost:8082/api-docs/

6. Authentication is based on a JWT token. Execute http://localhost:8082/authenticate   with userName "admin" , Password "password"


# Build the project 

1. mvn clean
2. mvn package
3. java -jar /target/transaction-category-service-1.0.0-SNAPSHOT.war

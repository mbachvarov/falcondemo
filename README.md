Simple data processing pipeline in the cloud.<br>
Application devolped in Java 8 using Spring Boot, Maven, MySQL database, Redis(Pub/Sub), Spring WebSockets implementation.<br>
<br>
--SET UP--
```sh
    The project contains docker-compose.yaml file. tha
    This makes building, testing and then runnig the application easy.
    Get started by running the following command:
        docker-compose up
```
--BASIC DESCRIPTION--
URL | METHOD | DESCRIPTION
--- | --- | ---
http://<docker-host>:8080/api/messages | GET | Returns all existing messages
http://<docker-host>:8080/api/messages | POST | Takes JSON input data and creates new message
http://<docker-host>:8080/messages/live | GET | Returns HTML page for live monitorng of the incomming messages
 
--DOCUMENTATION
```sh
    After runnig the application, full documentatino cn be found at:
    http://<docker-host>:8080/swagger-ui.html _UI version
    http://<docker-host>:8080/v2/api-docs      JSON version
```

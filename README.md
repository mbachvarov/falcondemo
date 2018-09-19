Simple data processing pipeline in the cloud.<br>
Application devolped in Java 8 using Spring Boot, Maven, MySQL database, Redis(Pub/Sub), Spring WebSockets implementation.<br>
<br>
--SET UP--<br>
____The project contains docker-compose.yaml file that makes building, testing and then runnig the application easy.<br>
____Get started by running the following command:<br>
_________docker-compose up<br>
<br>
--BASIC DESCRIPTION--<br>
____| URL | METHOD | DESCRIPTION|
____| --- | --- | ---|
____| http://<docker-host>:8080/api/messages | GET | Returns all existing messages |
____| http://<docker-host>:8080/api/messages | POST | Takes JSON input data and creates new message |
____| http://<docker-host>:8080/messages/live | GET | Returns HTML page for live monitorng of the incomming messages |
 
--DOCUMENTATION--<br>
____After runnig the application, full documentatino cn be found at:
________http://<docker-host>:8080/swagger-ui.html ____UI version
________http://<docker-host>:8080/v2/api-docs____ ____JSON version

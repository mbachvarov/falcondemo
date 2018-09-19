Simple data processing pipeline in the cloud.<br>
Application summary:
<ul>
   <li>A REST endpoint is taking a dummy JSON input, and the server puts the REST payload on Redis</li>
   <li>A Consumer is running in the application, taking the freshly received message and persists it in MySQL database</li>
   <li>A REST endpoint is implemented for retrieving all the messages persisted in JSON format from the database</li>
   <li>The message is also pushed through Websockets for listening browser clients at the time the message was received on the REST endpoint</li>
   <li>A simple HTML page is implemented to show the real time message delivery</li>
</ul>

The application is developed in Java 8 using:
<ul>
    <li>Spring Boot</li>
    <li>Maven</li>
    <li>MySQL</li>
    <li>Redis(Pub/Sub)</li>
    <li>Spring WebSockets implementation</li>
</ul>

--SET UP--
```sh
The project contains docker-compose.yaml file.
This makes building, testing and then runnig the application easy.
Get started by running the following command:
    <b>docker-compose up<b>
*Docker must be installed before running the command.
```
--BASIC DESCRIPTION--<br>
URL | METHOD | DESCRIPTION
--- | --- | ---
http://<docker-host>:8080/api/messages | GET | Returns all existing messages
http://<docker-host>:8080/api/messages | POST | Takes JSON input data and creates new message
http://<docker-host>:8080/messages/live | GET | Returns a simple HTML page for live monitoring of the received messages
 
--DOCUMENTATION---
```sh
After runnig the application, full documentatino cn be found at:
http://<docker-host>:8080/swagger-ui.html _UI version
http://<docker-host>:8080/v2/api-docs      JSON version
```

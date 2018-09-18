/**
* On docment ready
*/
$(document).ready(function() {
	// Web socket connection start.
	connect();
});

/**
* Web socket connection open and subscribe.
*/
function connect() {
	var socket = new SockJS('/websocket');
	var stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
	    stompClient.subscribe('*', function(message) {
	  	    addMessage(message.body);
	    });
	});

	return;
} 

/**
* Message append to display area
*/
function addMessage(message) {
	$("#messages-container").append(new Date().toLocaleString() + " --> " + message + "<br>");
}

var wsUri = "ws://" + document.location.host + document.location.pathname + "whiteboardendpoint";
var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) { onError(evt) };

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

// For testing purposes
var output = document.getElementById("output");
websocket.onopen = function(evt) { onOpen(evt) };

function writeToScreen(message) {
    output.innerHTML += message + "<br>";
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}
// End test functions
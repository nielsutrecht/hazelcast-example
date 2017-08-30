var stompClient = null;
var events = [];


function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    stompClient = Stomp.client('ws://localhost:8080/eventsink');
    stompClient.debug = null;
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/events', function(event){
            showGreeting(event.body);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
    events.push(message);

    var copy = Array.from(events);
    copy.reverse();

    var response = document.getElementById('response');
    response.textContent = copy.join('\n');
}

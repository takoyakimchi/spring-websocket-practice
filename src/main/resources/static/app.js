const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log('Connected: ' + frame);

  stompClient.subscribe('/topic/greetings', (greeting) => {
    let success = JSON.parse(greeting.body).isSuccess;
    let message = JSON.parse(greeting.body).content;

    if (success) {
      showGreeting(message);
    } else {
      alert(message);
    }
  });
};

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  }
  else {
    $("#conversation").hide();
  }
  $("#greetings").html("");
}

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  stompClient.publish({
    destination: "/publish/hello",
    body: JSON.stringify({'username': $("#na").val(), 'content': $("#name").val()})
  });
}

function showGreeting(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $( "#connect" ).click(() => connect());
  $( "#disconnect" ).click(() => disconnect());
  $( "#send" ).click(() => sendName());
});

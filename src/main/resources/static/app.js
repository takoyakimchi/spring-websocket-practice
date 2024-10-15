const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/ws-connect'
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log('Connected: ' + frame);

  stompClient.subscribe('/subscribe/chat', (greeting) => {
    let body = JSON.parse(greeting.body);
    let username = body.username;
    let content = body.content;
    showChat(username + ": " + content);
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

function sendChat() {
  stompClient.publish({
    destination: "/publish/chat",
    body: JSON.stringify({'username': $("#na").val(), 'content': $("#name").val()})
  });
  document.getElementById('name').value = '';
}

function showChat(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $( "#connect" ).click(() => connect());
  $( "#disconnect" ).click(() => disconnect());
  $( "#send" ).click(() => sendChat());
});

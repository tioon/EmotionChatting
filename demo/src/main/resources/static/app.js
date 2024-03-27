var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#chat").html("");
}

function connect(roomId) {
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + roomId, function (chat) {
            showChat(JSON.parse(chat.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage(roomId) {
    stompClient.send("/app/chat", {}, JSON.stringify({'roomId': roomId, 'message': $("#message").val()}));
}

function showChat(message) {
    $("#chat").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) { // submit 클릭 시 발동
        e.preventDefault();
    });

    $( "#connect" ).click(function() { // connect 클릭 시 발동
        var roomId = $("#roomId").val();
        connect(roomId);
    });

    $( "#disconnect" ).click(function() { // disconnect 클릭 시 발동
     disconnect();
    });

    $( "#send" ).click(function() { // send 클릭 시 발동
     var roomId = $("#roomId").val();
     sendMessage(roomId);
    });
});
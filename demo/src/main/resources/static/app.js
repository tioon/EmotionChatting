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

function connect(nickname, roomId) {
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/' + roomId, function (chat) { //구독
            showChat(JSON.parse(chat.body).content);
        });

        // 방입장 메세지 보내기
        stompClient.send("/app/room", {}, JSON.stringify({'roomId': roomId,'nickname': nickname,  'message': $("#message").val()}));
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage(nickname, roomId) {
    stompClient.send("/app/chat", {}, JSON.stringify({'roomId': roomId, 'nickname': nickname, 'message': $("#message").val()}));
}

function showChat(message) {
    $("#chat").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) { // submit 클릭 시 발동
        e.preventDefault();
    });

    $( "#connect" ).click(function() { // connect 클릭 시 발동
        var nickname = $("#nickname").val();
        var roomId = $("#roomId").val();
        connect(nickname, roomId);
    });

    $( "#disconnect" ).click(function() { // disconnect 클릭 시 발동
     disconnect();
    });

    $( "#send" ).click(function() { // send 클릭 시 발동
     var nickname = $("#nickname").val();
     var roomId = $("#roomId").val();
     sendMessage(nickname, roomId);
    });
});
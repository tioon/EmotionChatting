package chatting.domain.Room.controller;

import chatting.domain.Room.dto.request.Greeting;
import chatting.domain.Room.dto.request.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    //@SendTo("/topic/chat") 이건 어노테이션 활용방법
    public void chat(ChatMessage message) throws Exception {
        String roomId = message.getRoomId();
        messagingTemplate.convertAndSend("/topic/" + roomId, new Greeting(HtmlUtils.htmlEscape(message.getNickname()+ "님 : " + message.getMessage())));
    }

    @MessageMapping("/room")
    //@SendTo("/topic/chat") 이건 어노테이션 활용방법
    public void roomEnter(ChatMessage message) throws Exception {
        String roomId = message.getRoomId();
        messagingTemplate.convertAndSend("/topic/" + roomId,new Greeting(HtmlUtils.htmlEscape(message.getNickname() + "님이 입장하셨습니다.")));
    }


}
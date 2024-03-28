package chatting.domain.Room.controller;

import chatting.domain.Room.dto.request.ChatResponse;
import chatting.domain.Room.dto.request.ChatMessage;
import chatting.domain.Room.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    //@SendTo("/topic/chat") 이건 어노테이션 활용방법
    public void chat(ChatMessage message) throws Exception {
        chatService.sendChatting(message);
    }

    @MessageMapping("/room")
    //@SendTo("/topic/chat") 이건 어노테이션 활용방법
    public void roomEnter(ChatMessage message) throws Exception {
        chatService.roomEnter(message);
    }


}
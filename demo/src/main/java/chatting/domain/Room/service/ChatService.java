package chatting.domain.Room.service;

import chatting.domain.Room.dto.request.ChatMessage;
import chatting.domain.Room.dto.request.ChatResponse;
import chatting.domain.Room.entity.Room;
import chatting.domain.Room.repository.RoomRepository;
import chatting.global.redis.RedisMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final RoomRepository roomRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisMessagePublisher redisMessagePublisher;

    public void sendChatting(ChatMessage chatMessage){
        String roomId = chatMessage.getRoomId();
        //messagingTemplate.convertAndSend("/topic/" + roomId, ChatResponse.toResponse(HtmlUtils.htmlEscape(chatMessage.getNickname()+ "님 : " + chatMessage.getMessage())));
        redisMessagePublisher.sendMessage(chatMessage);

    }

    public void roomEnter(ChatMessage message){
        String roomId = message.getRoomId();
        //messagingTemplate.convertAndSend("/topic/" + roomId, ChatResponse.toResponse(HtmlUtils.htmlEscape(message.getNickname() + "님이 입장하셨습니다.")));
        redisMessagePublisher.roomEnter(message);
    }

}
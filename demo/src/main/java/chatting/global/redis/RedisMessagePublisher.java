package chatting.global.redis;

import chatting.domain.Room.dto.request.ChatMessage;
import chatting.domain.Room.dto.request.ChatResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {  // 메세지 발행자

    private final RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(ChatMessage chatMessage) {
        String topic = "chatting"; // roomId를 기반으로 한 동적 토픽 이름 생성
        String jsonMessage;

        try {
            Map<String,Object> map = new HashMap<>();
            map.put("roomId", chatMessage.getRoomId());
            map.put("message", chatMessage.getNickname() + "님: " +chatMessage.getMessage());
            jsonMessage = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        redisTemplate.convertAndSend(topic,jsonMessage); // 메세지 발행
    }

    public void roomEnter(ChatMessage chatMessage){
        String topic = "chatting"; // roomId를 기반으로 한 동적 토픽 이름 생성
        String jsonMessage;

        try {
            Map<String,String> map = new HashMap<>();
            map.put("roomId", chatMessage.getRoomId());
            map.put("message", chatMessage.getNickname() + "님이 입장하셨습니다.");
            jsonMessage = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.convertAndSend(topic,jsonMessage); // 메세지 발행
    }
}

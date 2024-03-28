package chatting.global.redis;

import chatting.domain.Room.dto.request.ChatMessage;
import chatting.domain.Room.dto.request.ChatResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisMessageSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    // 생성자 생략

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        log.info(message.toString());

        String jsonMessage = new String(body, StandardCharsets.UTF_8); // body를 UTF-8 문자열로 변환
        jsonMessage = jsonMessage.replaceFirst("^.*?(\\{)", "$1"); // 이상한 문자열 제거

        try {
            JsonNode rootNode = objectMapper.readTree(jsonMessage);
            String roomId = rootNode.get("roomId").asText(); // roomId 추출
            String messageContent = rootNode.get("message").asText(); // message 추출
            log.info(roomId + " " + messageContent);
            // 채팅 메시지를 WebSocket으로 클라이언트에게 전송
            messagingTemplate.convertAndSend("/topic/" + roomId, ChatResponse.toResponse(HtmlUtils.htmlEscape(messageContent)));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

package chatting.domain.Room.service;

import chatting.domain.Room.dto.request.ChatMessage;
import chatting.domain.Room.dto.request.ChatResponse;
import chatting.domain.Room.entity.Room;
import chatting.domain.Room.repository.RoomRepository;
import chatting.global.redis.RedisMessagePublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final RoomRepository roomRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisMessagePublisher redisMessagePublisher;
    private final RestTemplate restTemplate;
    @Value("${huggingface.token}")
    private String token;


    public void sendChatting(ChatMessage chatMessage){
        String roomId = chatMessage.getRoomId();
        //messagingTemplate.convertAndSend("/topic/" + roomId, ChatResponse.toResponse(HtmlUtils.htmlEscape(chatMessage.getNickname()+ "님 : " + chatMessage.getMessage())));
        List<Map.Entry<String, Double>> top3Emotions = emotionAnalyzeUsingHuggingFace(chatMessage.getMessage());
        redisMessagePublisher.sendMessage(chatMessage, top3Emotions);
    }

    public void roomEnter(ChatMessage message){
        String roomId = message.getRoomId();
        //messagingTemplate.convertAndSend("/topic/" + roomId, ChatResponse.toResponse(HtmlUtils.htmlEscape(message.getNickname() + "님이 입장하셨습니다.")));
        redisMessagePublisher.roomEnter(message);
    }
    public void roomExit(ChatMessage message){
        redisMessagePublisher.roomExit(message);
    }

    public List<Map.Entry<String, Double>> emotionAnalyzeUsingHuggingFace(String message) {
        String url = "https://api-inference.huggingface.co/models/bhadresh-savani/bert-base-go-emotion";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        String inputJson = String.format("{\"inputs\": \"%s\"}", message);
        HttpEntity<String> requestEntity = new HttpEntity<>(inputJson, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String responseBody = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            JsonNode resultsArray = jsonNode.get(0);

            List<Map.Entry<String, Double>> topEmotions = new ArrayList<>();
            for (JsonNode resultNode : resultsArray) {
                String label = resultNode.get("label").asText(); // 감정 이름
                Double score = resultNode.get("score").asDouble(); // 감정 점수

                topEmotions.add(new AbstractMap.SimpleEntry<>(label, score));
            }

            // 상위 3개 감정 추출
            Collections.sort(topEmotions, (a, b) -> Double.compare(b.getValue(), a.getValue()));
            List<Map.Entry<String, Double>> top3Emotions = topEmotions.subList(0, Math.min(3, topEmotions.size()));

            for (Map.Entry<String, Double> emotion : top3Emotions) {
                System.out.println(emotion.getKey() + " " + emotion.getValue());
            }
            return top3Emotions;

        } catch (HttpServerErrorException e) {
            e.printStackTrace();
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
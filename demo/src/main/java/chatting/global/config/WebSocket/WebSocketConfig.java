package chatting.global.config.WebSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic"); // 메세지 구독자 ("/sub" 경로로 메세지를 받으면 구독자들에게 전달)
        config.setApplicationDestinationPrefixes("/app"); // 메세지 발행자 ("/app" 경로로 메시지 주면 가공 후 구독자들에게 전달)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
        // 커넥션 경로 설정
    }
}

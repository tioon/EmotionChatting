package chatting.global.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    //웹소켓 연결 끊어 졌을때,
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // 세션ID
        String sessionId = headerAccessor.getSessionId();
        System.out.println("웹소켓 연결 끊김: 세션 ID = " + sessionId);

        // 구독 정보를 얻습니다.
        // 구독을 식별하는 ID는 사용자가 구독할 때 설정하거나, 자동으로 생성됩니다.
        String subscriptionId = headerAccessor.getSubscriptionId();
            System.out.println("구독 ID: " + subscriptionId);

        // 사용자가 구독한 대상(destination)을 얻습니다.
        String destination = headerAccessor.getDestination();

            System.out.println("구독 대상: " + destination);


    }
}

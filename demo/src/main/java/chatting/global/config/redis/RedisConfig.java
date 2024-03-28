package chatting.global.config.redis;

import chatting.global.redis.RedisMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    // Redis 컨테이너
    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer(); // Redis 컨테이너 생성
        container.setConnectionFactory(connectionFactory); // Redis 서버와 연결
        container.addMessageListener(listenerAdapter, new PatternTopic("chatting")); // 구독 Topic
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }


}

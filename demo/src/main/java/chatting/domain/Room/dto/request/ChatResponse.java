package chatting.domain.Room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse implements Serializable { // Redis를 사용하므로 직렬화가 필요
    private String content;

    public static ChatResponse toResponse(String content){
        return new ChatResponse(content);
    }
}
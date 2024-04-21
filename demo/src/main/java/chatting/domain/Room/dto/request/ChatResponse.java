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
    private String emotion1;
    private String emotion2;
    private String emotion3;

    public static ChatResponse toResponse(String content,String emotion1, String emotion2, String emotion3){
        return new ChatResponse(content,emotion1, emotion2,emotion3);
    }
}
package chatting.domain.Room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String content;

    public static ChatResponse toResponse(String content){
        return new ChatResponse(content);
    }
}
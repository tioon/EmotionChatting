package chatting.domain.auth.dto.Response;


import chatting.domain.auth.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;

    public static UserResponseDto of(Member member) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(member.getId());
        userResponseDto.setEmail(member.getEmail());
        return userResponseDto;
    }

}
package chatting.global.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TokenDto {
    private String grantType;
    private String accessToken;
    private long accessTokenExpiresIn;
    private String refreshToken;

    // 생성자, getter, setter 등 필요한 메서드를 구현합니다.

    @Builder
    public TokenDto(String grantType, String accessToken, long accessTokenExpiresIn, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
    }

    // getter, setter 등 필요한 메서드를 추가로 구현합니다.
}

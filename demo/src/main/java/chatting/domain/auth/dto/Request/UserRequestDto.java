package chatting.domain.auth.dto.Request;

import chatting.domain.auth.entity.Authority;
import chatting.domain.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String email;
    private String password;

    public Member toUser(PasswordEncoder passwordEncoder) {
        return new Member(email, passwordEncoder.encode(password), Authority.ROLE_USER);
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}

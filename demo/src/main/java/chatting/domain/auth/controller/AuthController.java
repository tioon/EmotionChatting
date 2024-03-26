package chatting.domain.auth.controller;

import chatting.domain.auth.dto.Request.TokenRequestDto;
import chatting.domain.auth.dto.Request.UserRequestDto;
import chatting.domain.auth.dto.Response.UserResponseDto;
import chatting.domain.auth.service.AuthService;
import chatting.global.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html을 찾아서 렌더링
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // signup.html을 찾아서 렌더링
    }

    @GetMapping("/reissue")
    public String reissue() {
        return "reissue"; // reissue.html을 찾아서 렌더링
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestParam("email") String email, @RequestParam("password") String password) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail(email);
        userRequestDto.setPassword(password);
        return ResponseEntity.ok(authService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail(email);
        userRequestDto.setPassword(password);
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken) {
        TokenRequestDto tokenRequestDto = new TokenRequestDto();
        tokenRequestDto.setAccessToken(accessToken);
        tokenRequestDto.setRefreshToken(refreshToken);
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}

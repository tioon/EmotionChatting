package chatting.global.config.security;

import chatting.global.jwt.JwtAccessDeniedHandler;
import chatting.global.jwt.JwtAuthenticationEntryPoint;
import chatting.global.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import static com.google.common.base.Predicates.and;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable();
        http.headers().frameOptions().disable(); // H2 콘솔 사용을 위한 설정
        http.formLogin().disable(); // 기본 로그인 페이지 사용하지 않음
        http.httpBasic().disable(); // HTTP Basic 인증 사용하지 않음

        // cors 필터 추가
        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);

        // exception handling 할 때 우리가 만든 클래스를 추가
        //http.exceptionHandling();
        //.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        //.accessDeniedHandler(jwtAccessDeniedHandler);

        http.headers()
        .frameOptions()
        .sameOrigin();

        // 시큐리티는 기본적으로 세션을 사용
        // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
        http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
        http.authorizeHttpRequests()
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated();   // 나머지 API 는 전부 인증 필요


        http.logout() // 로그아웃시 리다이렉트 URL 설정
                .logoutSuccessUrl("/auth/login");

        // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
        http.apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

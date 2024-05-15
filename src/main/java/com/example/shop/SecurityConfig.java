package com.example.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


//이 어노테이션들을 붙이면 스프링 시큐리티 관리하는 어노테이션
@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    //필터 체인 - 유저의 요청과 서버 응답 사이에 자동실행하고싶은 코드를 담는 것
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csrf - 다른 사이트에 폼 만들어서 내 사이트로 요청을 보내는식으로 공격할수 있음
        //csrf를 키면 내가 보내는 데이터에 랜덤 문자도 같이 전송해서 서버가 내가 보냈는지 확인

        //csrf 끄기
        http.csrf((csrf) -> csrf.disable());


        //로그인 페이지만 csrf 끄고 다른 사이트에서는 키기
//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
//                .ignoringRequestMatchers("/login")
//        );

        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );
        //폼으로 로그인하겟다, 로그인이 되면 list.html로 이동
        http.formLogin((formLogin) ->
                formLogin.loginPage("/login").defaultSuccessUrl("/list")
        );

        // /logout 주소로 get 요청 보내면 로그아웃이 된다
        http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/list"));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
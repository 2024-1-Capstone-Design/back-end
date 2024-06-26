package com.hangeulbada.domain.auth.component;

import com.hangeulbada.domain.auth.dto.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 45L;

    private final JwtTokenProvider jwtTokenProvider;

    //id 받아 Access Token 생성
    public AuthTokens generate(String id) {

        String accessToken = jwtTokenProvider.accessTokenGenerate(id);
        String refreshToken = jwtTokenProvider.refreshTokenGenerate();

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
}

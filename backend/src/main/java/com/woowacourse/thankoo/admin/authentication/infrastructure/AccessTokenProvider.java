package com.woowacourse.thankoo.admin.authentication.infrastructure;

import com.woowacourse.thankoo.admin.authentication.domain.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenProvider implements TokenProvider {

    private final SecretKey key;
    private final long validity;

    public AccessTokenProvider(@Value("${admin.token.key}") final String key,
                               @Value("${admin.token.validity}") final long validity) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.validity = validity;
    }

    @Override
    public String create(final String payload) {
        Date now = new Date();
        Date endOfValidity = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(endOfValidity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

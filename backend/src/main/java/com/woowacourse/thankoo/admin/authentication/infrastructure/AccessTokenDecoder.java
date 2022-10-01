package com.woowacourse.thankoo.admin.authentication.infrastructure;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.woowacourse.thankoo.admin.authentication.exception.InvalidTokenException;
import com.woowacourse.thankoo.admin.authentication.presentation.TokenDecoder;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDecoder implements TokenDecoder {

    private final SecretKey key;

    public AccessTokenDecoder(@Value("${admin.token.key}") final String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(UTF_8));
    }

    @Override
    public String decode(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new InvalidTokenException(AdminErrorType.INVALID_TOKEN);
        }
    }
}

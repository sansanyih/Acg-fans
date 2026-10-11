package com.example.acgdata.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具：生成 / 解析 Token。
 * 签名算法 HS256（密钥长度必须 >= 32 字节），载荷里只放 userId、username、admin，
 * 不放任何敏感信息。
 */
@Component
public class JwtUtils {

    @Value("${acg.jwt.secret}")
    private String secret;

    @Value("${acg.jwt.expire-minutes:720}")
    private long expireMinutes;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String username, boolean admin) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireMinutes * 60 * 1000);
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("admin", admin)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key())
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public LoginUser parseToLoginUser(String token) {
        Claims claims = parseClaims(token);
        Long userId = Long.valueOf(claims.getSubject());
        String username = claims.get("username", String.class);
        Boolean admin = claims.get("admin", Boolean.class);
        return new LoginUser(userId, username, Boolean.TRUE.equals(admin));
    }

    /** Token 有效期（秒），返回给前端用于自动续期 / 过期提示 */
    public long getExpireSeconds() {
        return expireMinutes * 60;
    }
}

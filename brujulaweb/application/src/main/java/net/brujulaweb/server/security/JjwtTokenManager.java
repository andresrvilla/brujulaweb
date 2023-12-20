package net.brujulaweb.server.security;

import domain.brujulaweb.security.TokenManager;


import io.javalin.http.ForbiddenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import net.brujulaweb.repository.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;

public class JjwtTokenManager implements TokenManager {

    private static final Logger logger = LoggerFactory.getLogger(JjwtTokenManager.class);
    private final Key key;

    public JjwtTokenManager() {
        //this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.key = Keys.hmacShaKeyFor(Config.get("server.security.jwtkey").getBytes());
    }

    @Override
    public String issueToken(String userId) {
        return Jwts.builder().
                setSubject(userId)
                .signWith(key)
                .compact();
    }

    @Override
    public boolean authorize(String token, String userId) {
        try {
            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return subject.equalsIgnoreCase(userId);
        } catch (SignatureException ex) {
            logger.info(String.format("Invalid signature when login user %s", userId));
            throw new ForbiddenResponse();
        } catch (
                Exception ex) {
            logger.error("authorize Exception", ex);
            throw new ForbiddenResponse();
        }
    }
}

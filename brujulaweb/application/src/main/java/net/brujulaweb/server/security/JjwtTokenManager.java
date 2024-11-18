package net.brujulaweb.server.security;

import domain.brujulaweb.security.TokenManager;


import domain.brujulaweb.util.DateUtils;
import io.javalin.http.ForbiddenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import net.brujulaweb.repository.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

public class JjwtTokenManager implements TokenManager {

    private static final Logger logger = LoggerFactory.getLogger(JjwtTokenManager.class);
    private final Key key;

    public JjwtTokenManager() {
        //this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.key = Keys.hmacShaKeyFor(Config.get("server.security.jwtkey").getBytes());
    }

    @Override
    public String issueToken(String userId) {
        Date expirationDate = Date.from(DateUtils.now().plusMinutes(60).toInstant());
        return Jwts.builder()
                .setExpiration(expirationDate)
                .setSubject(userId)
                .signWith(key)
                .compact();
    }

    @Override
    public boolean authorize(String token, String userId) {
        try {
            String subject = getClaims(token)
                    .getSubject();

            return subject.equalsIgnoreCase(userId) && getClaims(token).getExpiration().after( Date.from(DateUtils.now().toInstant()));
        } catch (SignatureException ex) {
            logger.info(String.format("Invalid signature when login user %s", userId));
            throw new ForbiddenResponse();
        } catch (
                Exception ex) {
            logger.error("authorize Exception", ex);
            throw new ForbiddenResponse();
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

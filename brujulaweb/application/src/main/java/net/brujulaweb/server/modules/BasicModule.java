package net.brujulaweb.server.modules;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import domain.brujulaweb.security.TokenManager;
import domain.brujulaweb.util.Encrypter;
import net.brujulaweb.server.security.JjwtTokenManager;
import net.brujulaweb.util.BcryptEncrypter;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TokenManager.class).to(JjwtTokenManager.class);
        bind(Encrypter.class).to(BcryptEncrypter.class);
        bind(ObjectMapper.class).toInstance(new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        );
    }
}
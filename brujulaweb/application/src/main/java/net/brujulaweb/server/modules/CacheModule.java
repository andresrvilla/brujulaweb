package net.brujulaweb.server.modules;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import domain.brujulaweb.entities.association.Association;
import domain.brujulaweb.entities.user.User;

import java.util.concurrent.TimeUnit;

public class CacheModule extends AbstractModule {

    @Override
    protected void configure() {
        bind( new TypeLiteral<Cache<String, User >>(){})
                .toInstance(Caffeine.newBuilder()
                        .expireAfterWrite(120, TimeUnit.MINUTES)
                        .maximumSize(100)
                        .build());

        bind( new TypeLiteral<Cache<String, Association>>(){})
                .toInstance(Caffeine.newBuilder()
                        .expireAfterWrite(120, TimeUnit.MINUTES)
                        .maximumSize(100)
                        .build());
    }
}

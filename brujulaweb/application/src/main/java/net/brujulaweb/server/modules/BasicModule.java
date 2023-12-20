package net.brujulaweb.server.modules;

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
    }
}
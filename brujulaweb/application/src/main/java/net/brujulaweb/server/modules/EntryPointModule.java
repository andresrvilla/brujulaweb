package net.brujulaweb.server.modules;

import com.google.inject.AbstractModule;
import net.brujulaweb.server.endpoints.users.UserEndpoints;

public class EntryPointModule  extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserEndpoints.class);
    }
}

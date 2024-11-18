package net.brujulaweb.server.modules;

import com.google.inject.AbstractModule;
import domain.brujulaweb.usecases.AssociationUseCase;
import domain.brujulaweb.usecases.UserManagementUseCase;

public class UseCaseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserManagementUseCase.class);
        bind(AssociationUseCase.class);
    }
}

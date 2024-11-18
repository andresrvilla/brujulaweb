package net.brujulaweb.server.modules;

import com.google.inject.AbstractModule;
import domain.brujulaweb.repository.AssociationRepository;
import domain.brujulaweb.repository.UserRepository;
import net.brujulaweb.repository.association.AssociacionDBRepository;
import net.brujulaweb.repository.config.DBHandler;
import net.brujulaweb.repository.user.UserDBRepository;

import javax.sql.DataSource;

public class RepositoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataSource.class).toInstance(DBHandler.getDataSource());

        bind(UserRepository.class).to(UserDBRepository.class);
        bind(AssociationRepository.class).to(AssociacionDBRepository.class);
    }
}

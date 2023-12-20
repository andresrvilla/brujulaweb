package net.brujulaweb.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import domain.brujulaweb.usecases.UserManagementUseCase;
import domain.brujulaweb.util.Encrypter;
import io.javalin.Javalin;
import net.brujulaweb.repository.config.DBHandler;
import net.brujulaweb.repository.user.UserDBRepository;
import net.brujulaweb.server.endpoints.users.UserEndpoints;
import net.brujulaweb.server.modules.BasicModule;
import net.brujulaweb.server.modules.EntryPointModule;
import net.brujulaweb.server.modules.RepositoryModule;
import net.brujulaweb.server.modules.UseCaseModule;
import net.brujulaweb.server.security.JjwtTokenManager;
import net.brujulaweb.util.BcryptEncrypter;


public class BrujulaWebServer {
    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig ->
                {
                    javalinConfig.staticFiles.add("/app");
                    javalinConfig.spaRoot.addFile("/", "/app/index.html");
                    //javalinConfig.plugins.enableDevLogging();
                });

        Injector injector = Guice.createInjector(
                new BasicModule(),
                new EntryPointModule(),
                new RepositoryModule(),
                new UseCaseModule()
                );

        UserEndpoints userEndpoints = injector.getInstance(UserEndpoints.class);

        app.before("/api/protected/*", userEndpoints::authorize);
        app.post("/api/login",userEndpoints::login);
        app.post("/api/signup",userEndpoints::signup);

        app.start(8080);
    }
}

package net.brujulaweb.server;

import domain.brujulaweb.usecases.UserManagementUseCase;
import domain.brujulaweb.util.Encrypter;
import io.javalin.Javalin;
import net.brujulaweb.repository.config.DBHandler;
import net.brujulaweb.repository.user.UserDBRepository;
import net.brujulaweb.server.endpoints.users.UserEndpoints;
import net.brujulaweb.server.security.JjwtTokenManager;
import net.brujulaweb.util.BcryptEncrypter;


public class BrujulaWebServer {
    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig ->
                {
                    javalinConfig.staticFiles.add("/app");
                    javalinConfig.spaRoot.addFile("/", "/app/index.html");
                    javalinConfig.plugins.enableDevLogging();
                });
        JjwtTokenManager tokenManager = new JjwtTokenManager();
        UserDBRepository userRepository = new UserDBRepository(DBHandler.getDataSource());
        Encrypter encrypter = new BcryptEncrypter();
        UserManagementUseCase useCase = new UserManagementUseCase(tokenManager,encrypter,userRepository);
        UserEndpoints userEndpoints = new UserEndpoints(useCase);

        app.before("/api/protected/*", userEndpoints::authorize);
        app.post("/api/login",userEndpoints::login);
        app.post("/api/signup",userEndpoints::signup);

        app.start(8080);
    }
}

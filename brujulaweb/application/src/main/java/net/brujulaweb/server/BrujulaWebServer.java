package net.brujulaweb.server;

import domain.brujulaweb.usecases.UserManagementUseCase;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import net.brujulaweb.repository.MockedUserRepository;
import net.brujulaweb.server.endpoints.users.UserEndpoints;
import net.brujulaweb.server.security.JjwtTokenManager;

public class BrujulaWebServer {
    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig ->
                {
                    javalinConfig.staticFiles.add("/app");
                    javalinConfig.spaRoot.addFile("/", "/app/index.html");
                    javalinConfig.plugins.enableDevLogging();
                });
        JjwtTokenManager tokenManager = new JjwtTokenManager();
        MockedUserRepository mockedUserRepository = new MockedUserRepository();
        UserManagementUseCase useCase = new UserManagementUseCase(tokenManager,mockedUserRepository);
        UserEndpoints userEndpoints = new UserEndpoints(useCase);

        app.before("/api/protected/*", userEndpoints::authorize);
        app.post("/api/login",userEndpoints::login);
        app.post("/api/signup",userEndpoints::signup);

        app.start(8080);
    }
}

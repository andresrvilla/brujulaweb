package net.brujulaweb.server.endpoints.users;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import domain.brujulaweb.entities.user.AuthResponse;
import domain.brujulaweb.entities.user.AuthRequest;
import domain.brujulaweb.exceptions.EntityConflictException;
import domain.brujulaweb.exceptions.UnauthorizedException;
import domain.brujulaweb.usecases.UserManagementUseCase;
import io.javalin.Javalin;
import io.javalin.http.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@AllArgsConstructor(onConstructor = @__({@Inject}))
@Singleton
public class UserEndpoints {
    private final UserManagementUseCase useCase;

    public void registerEndpoints(Javalin app) {
        app.before("/api/protected/*", this::authorize);
        app.post("/api/login", this::login);
        app.post("/api/signup", this::signup);
    }

    private void login(Context context) {
        AuthRequest request = context.bodyAsClass(AuthRequest.class);

        if (StringUtils.isEmpty(request.getEmail()) ||
                StringUtils.isEmpty(request.getPassword())) {
            throw new BadRequestResponse("Invalid login data");
        }

        try {
            AuthResponse result = useCase.login(request);
            context.json(result);
        } catch (UnauthorizedException exception) {
            throw new ForbiddenResponse();
        }

    }

    private void signup(Context context) {
        AuthRequest request = context.bodyAsClass(AuthRequest.class);

        if (StringUtils.isEmpty(request.getEmail()) ||
                StringUtils.isEmpty(request.getPassword())) {
            throw new BadRequestResponse("Invalid registration data");
        }

        try {
            AuthResponse result = useCase.signup(request);
            context.json(result);
        } catch (EntityConflictException exception) {
            throw new ConflictResponse();
        }
    }

    private void authorize(Context context) {
        String token = context.header("Authorization");
        String userId = context.header("X-User-ID");
        if (StringUtils.isEmpty(token) ||
                StringUtils.isEmpty(userId)) {
            throw new UnauthorizedResponse();
        }

        boolean result = useCase.authorize(token, userId);

        if (!result) {
            throw new UnauthorizedResponse();
        }

        context.sessionAttribute("user_id", userId);
    }

}

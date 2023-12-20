package net.brujulaweb.server.endpoints.users;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import domain.brujulaweb.entities.user.AuthResponse;
import domain.brujulaweb.entities.user.AuthRequest;
import domain.brujulaweb.exceptions.EntityConflictException;
import domain.brujulaweb.exceptions.UnauthorizedException;
import domain.brujulaweb.usecases.UserManagementUseCase;
import io.javalin.http.ConflictResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import lombok.AllArgsConstructor;


@AllArgsConstructor(onConstructor = @__({ @Inject}))
@Singleton
public class UserEndpoints {
    private UserManagementUseCase useCase;

    public void login(Context context) {
        //Validator validator = new Validator();
        AuthRequest request = context.bodyAsClass(AuthRequest.class);
        /*List<ConstraintViolation> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getName().concat(": ").concat(v.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new BadRequestResponse(message);
        }*/
        try {
            AuthResponse result = useCase.login(request);
            context.json(result);
        } catch (UnauthorizedException exception) {
            throw new ForbiddenResponse();
        }

    }

    public void signup(Context context) {
        AuthRequest request = context.bodyAsClass(AuthRequest.class);
        /*Validator validator = new Validator();
        List<ConstraintViolation> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getName().concat(": ").concat(v.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new BadRequestResponse(message);
        }*/
        try {
            AuthResponse result = useCase.signup(request);
            context.json(result);
        } catch (EntityConflictException exception) {
            throw new ConflictResponse();
        }
    }

    public void authorize(Context context) {
        String token = context.header("Authorization");
        String userId = context.header("X-User-ID");
        if (token == null) {
            throw new ForbiddenResponse();
        }
        boolean result = useCase.authorize(token, userId);
        if (!result) {
            throw new ForbiddenResponse();
        }
    }
}

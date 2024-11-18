package net.brujulaweb.server.endpoints;

import com.google.inject.Inject;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.usecases.UserManagementUseCase;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@AllArgsConstructor(onConstructor = @__({@Inject}))
public abstract class Endpoints {

    private final UserManagementUseCase useCase;

    protected User getUser(Context context) {
        String userId = context.sessionAttribute("user_id");

        if(StringUtils.isEmpty(userId)){
            throw new BadRequestResponse();
        }

        return useCase.findByEmail(userId);
    }
}

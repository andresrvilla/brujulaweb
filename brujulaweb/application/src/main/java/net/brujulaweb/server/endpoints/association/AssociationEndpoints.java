package net.brujulaweb.server.endpoints.association;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import domain.brujulaweb.entities.association.Association;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.usecases.AssociationUseCase;
import domain.brujulaweb.usecases.UserManagementUseCase;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.NotFoundResponse;
import net.brujulaweb.server.dto.association.AssociationDto;
import net.brujulaweb.server.dto.association.AssociationDtoBuilder;
import net.brujulaweb.server.endpoints.Endpoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.*;


@Singleton
public class AssociationEndpoints extends Endpoints {
    private final AssociationUseCase associationUseCase;

    private final AssociationDtoBuilder associationDtoBuilder;

    private static final Logger logger = LoggerFactory.getLogger(AssociationEndpoints.class);

    @Inject
    public AssociationEndpoints(UserManagementUseCase userManagementUseCase,
                                AssociationUseCase associationUseCase,
                                AssociationDtoBuilder associationDtoBuilder) {
        super(userManagementUseCase);
        this.associationUseCase = associationUseCase;
        this.associationDtoBuilder = associationDtoBuilder;
    }

    public void registerEndpoints(Javalin app) {
        app.routes(
            () -> {
                path("/api/protected/association/", () -> {
                    get("/", this::getUserAssociation);
                });
            }
        );
    }

    private void getUserAssociation(Context context) {
        User user = getUser(context);
        Association association = associationUseCase.get(user);

        if(Objects.isNull(association)){
            throw new NotFoundResponse();
        }

        try {
            context.json(associationDtoBuilder.build(association));
        } catch (JsonProcessingException e) {
            logger.error("Error on getUserAssociation", e);
            throw new InternalServerErrorResponse();
        }
    }

}

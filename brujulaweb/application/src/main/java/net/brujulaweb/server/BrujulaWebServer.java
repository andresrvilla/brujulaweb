package net.brujulaweb.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import net.brujulaweb.server.endpoints.association.AssociationEndpoints;
import net.brujulaweb.server.endpoints.users.UserEndpoints;
import net.brujulaweb.server.modules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BrujulaWebServer {
    private static final Logger logger = LoggerFactory.getLogger(BrujulaWebServer.class);

    public static void main(String[] args) {

        var app = Javalin.create(javalinConfig ->
        {
            javalinConfig.staticFiles.add("/app");
            javalinConfig.spaRoot.addFile("/", "/app/index.html");
            //javalinConfig.plugins.enableDevLogging();

            javalinConfig.plugins.enableRouteOverview("/overview");                      // show all routes on specified path
            //javalinConfig.plugins.enableRouteOverview(path, roles);               // show all routes on specified path (with auth)
            //javalinConfig.plugins.register(new RouteOverviewPlugin(path));        // show all routes on specified path
            //javalinConfig.plugins.register(new RouteOverviewPlugin(path, roles)); // show all routes on specified path (with auth)
        });

        Injector injector = Guice.createInjector(
                new BasicModule(),
                new EntryPointModule(),
                new RepositoryModule(),
                new UseCaseModule(),
                new CacheModule()
        );

        app.before("*", (ctx) -> System.out.println(ctx.url()));

        UserEndpoints userEndpoints = injector.getInstance(UserEndpoints.class);
        userEndpoints.registerEndpoints(app);
        AssociationEndpoints associationEndpoints = injector.getInstance(AssociationEndpoints.class);
        associationEndpoints.registerEndpoints(app);

        app.start(8080);
    }
}

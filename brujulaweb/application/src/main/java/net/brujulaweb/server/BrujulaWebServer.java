package net.brujulaweb.server;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class BrujulaWebServer {
    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig ->
                {
                    javalinConfig.staticFiles.add("/app");
                    javalinConfig.spaRoot.addFile("/", "/app/index.html");
                });

        

        app.start(8080);
    }
}

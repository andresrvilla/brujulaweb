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
                //.get("/", ctx -> ctx.result("Hello World"));



        app.start(8080);
    }
}

package net.brujulaweb.server;

import io.javalin.Javalin;

public class BrujulaWebServer {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);
    }
}

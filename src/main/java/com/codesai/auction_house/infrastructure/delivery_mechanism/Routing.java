package com.codesai.auction_house.infrastructure.delivery_mechanism;

import spark.Response;

import java.util.Optional;

import static spark.Spark.*;

public class Routing {

    public final static Integer PORT = 9001;

    public static void startApi() {
        System.out.println("starting auction house API");
        port(PORT);

        get("status", (req, res) -> "OK");
        path("api/", () -> {
            get("auction/:id", (request, response) -> wipResponse(response));
            post("auction", (request, response) -> wipResponse(response));
            post("auction/:id/bid", (request, response) -> wipResponse(response));
            post("auction/:auction_id/conquer", (request, response) -> wipResponse(response));
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        options("/*", (request, response) -> {
            var accessControlRequestHeaders = Optional.ofNullable(request.headers("Access-Control-Request-Headers"));
            accessControlRequestHeaders.ifPresent(c -> response.header("Access-Control-Allow-Headers", c));
            var accessControlRequestMethod = Optional.ofNullable(request.headers("Access-Control-Request-Method"));
            accessControlRequestMethod.ifPresent(c -> response.header("Access-Control-Allow-Methods", c));
            return "OK";
        });


    }

    private static Object wipResponse(Response response) {
        response.status(500);
        return "WIP";
    }
}

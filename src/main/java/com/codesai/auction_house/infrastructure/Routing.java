package com.codesai.auction_house.infrastructure;

import spark.Spark;

import java.util.Optional;

import static spark.Spark.*;

public class Routing {

    public final static Integer PORT = 9001;

    public static void Routes() {
        Spark.port(PORT);

        path("api/", () -> {
            get("status", (req, res) -> "OK");
            get("auction/:id", AuctionHouseAPI::retrieveAuction);
            post("auction", AuctionHouseAPI::createAuction);
        });

        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        options("/*", (request, response) -> {
            var accessControlRequestHeaders = Optional.ofNullable(request.headers("Access-Control-Request-Headers"));
            accessControlRequestHeaders.ifPresent(c -> response.header("Access-Control-Allow-Headers", c));
            var accessControlRequestMethod = Optional.ofNullable(request.headers("Access-Control-Request-Method"));
            accessControlRequestMethod.ifPresent(c -> response.header("Access-Control-Allow-Methods", c));
            return "OK";
        });


    }
}

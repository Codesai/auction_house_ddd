package com.codesai.auction_house.infrastructure;

import spark.Spark;

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

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });


    }
}

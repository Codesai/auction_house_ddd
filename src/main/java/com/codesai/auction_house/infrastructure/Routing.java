package com.codesai.auction_house.infrastructure;

import java.util.Optional;

import static spark.Spark.*;

public class Routing {

    public final static Integer PORT = 9001;

    public static void Routes() {
        port(PORT);

        get("status", (req, res) -> "OK");
        path("api/", () -> {
            get("auction/:id", (request, response) -> new AuctionHouseAPI(request, response).retrieveAuction());
            post("auction", (request, response) -> new AuctionHouseAPI(request, response).createAuction());
            post("auction/:id/bid", (request, response) -> new AuctionHouseAPI(request, response).bidAuction());
            post("auction/:auction_id/conquer", (request, response) -> new AuctionHouseAPI(request, response).conquerAuction());
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
}

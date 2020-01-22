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
    }
}

package com.codesai.auction_house.infrastructure;

import spark.Request;
import spark.Response;

import java.util.UUID;

public class AuctionHouseAPI {
    public static Object createAuction(Request request, Response response) {
        response.status(201);
        response.header("Location", "http://localhost:9001/api/auction/" + UUID.randomUUID());
        return "OK";
    }
}

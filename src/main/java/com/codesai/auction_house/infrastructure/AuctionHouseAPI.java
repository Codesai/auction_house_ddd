package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

public class AuctionHouseAPI {
    public static Object createAuction(Request request, Response response) {
        var auctionId = ActionFactory.createAuction().execute(createAuctionCommandFrom(request.body()));
        response.status(201);
        response.header("Content-type", "application/json");
        response.header("Location", request.url() + "/" + auctionId);
        return auctionId;
    }

    private static CreateAuctionCommand createAuctionCommandFrom(String body) {
        var json = new Gson().fromJson(body, JsonObject.class);
        return new CreateAuctionCommand(
                json.get("item").getAsJsonObject().get("name").getAsString(),
                json.get("item").getAsJsonObject().get("description").getAsString(),
                json.get("initial_bid").getAsDouble(),
                json.get("conquer_price").getAsDouble(),
                LocalDate.parse(json.get("expiration_date").getAsString()),
                json.get("minimum_overbidding_price").getAsDouble()
        );
    }
}

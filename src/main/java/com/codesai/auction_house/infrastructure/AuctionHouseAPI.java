package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

public class AuctionHouseAPI {
    public static Object createAuction(Request request, Response response) {
        try {
            var command = createAuctionCommandFrom(request.body());
            var auctionId = ActionFactory.createAuction().execute(command);
            response.status(201);
            response.header("Content-type", "application/json");
            response.header("Location", request.url() + "/" + auctionId);
            return auctionId;
        } catch (IllegalArgumentException e) {
            response.status(422);
            return "The field " + e.getMessage() + " value is not valid";
        }
    }

    private static CreateAuctionCommand createAuctionCommandFrom(String body) {
        var json = new Gson().fromJson(body, JsonObject.class);
        return new CreateAuctionCommand(
                json.get("item").getAsJsonObject().get("name").getAsString(),
                json.get("item").getAsJsonObject().get("description").getAsString(),
                getInitialBid(json),
                json.get("conquer_price").getAsDouble(),
                getExpirationDate(json),
                json.get("minimum_overbidding_price").getAsDouble()
        );
    }

    private static double getInitialBid(JsonObject json) {
        var fieldName = "initial_bid";
        try {
            return json.get(fieldName).getAsDouble();
        } catch (Exception e) {
            throw new IllegalArgumentException(fieldName, e);
        }
    }

    private static LocalDate getExpirationDate(JsonObject json) throws IllegalArgumentException {
        var fieldName = "expiration_date";
        try {
            return LocalDate.parse(json.get(fieldName).getAsString());
        } catch (Exception e) {
            throw new IllegalArgumentException(fieldName, e);
        }
    }
}

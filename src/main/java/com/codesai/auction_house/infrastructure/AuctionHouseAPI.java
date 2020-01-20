package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Optional;

public class AuctionHouseAPI {
    public static String createAuction(Request request, Response response) {
        var command = createAuctionCommandFrom(request.body());
        if (command.isPresent()) {
            var auctionId = ActionFactory.createAuction().execute(command.get());
            response.status(201);
            response.header("Content-type", "application/json");
            response.header("Location", request.url() + "/" + auctionId);
            response.body(auctionId);
            return auctionId;
        }
        response.status(422);
        return "The auction body is not well formed.";
    }

    private static Optional<CreateAuctionCommand> createAuctionCommandFrom(String body) {
        try {
            var json = new Gson().fromJson(body, JsonObject.class);
            return Optional.of(new CreateAuctionCommand(
                    json.get("item").getAsJsonObject().get("name").getAsString(),
                    json.get("item").getAsJsonObject().get("description").getAsString(),
                    json.get("initial_bid").getAsDouble(),
                    json.get("conquer_price").getAsDouble(),
                    LocalDate.parse(json.get("expiration_date").getAsString()),
                    json.get("minimum_overbidding_price").getAsDouble()
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

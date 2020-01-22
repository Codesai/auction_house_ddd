package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.commands.CreateAuctionCommand;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Optional;

import static com.codesai.auction_house.infrastructure.ActionFactory.retrieveAuctionAction;
import static org.eclipse.jetty.http.HttpStatus.*;


public class AuctionHouseAPI {
    public static String createAuction(Request request, Response response) {
        var command = createAuctionCommandFrom(request.body());
        if (command.isPresent()) {
            var auctionId = ActionFactory.createAuction().execute(command.get());
            response.status(CREATED_201);
            response.header("Content-type", "application/json");
            response.header("Location", request.url() + "/" + auctionId);
            response.body(auctionId);
            return auctionId;
        }
        response.status(422);
        return "The auction body is not well formed.";
    }

    public static String retrieveAuction(Request request, Response response) throws JSONException {
        var auctionId = request.params("id");
        var optionalAuction = retrieveAuctionAction().execute(new RetrieveAuctionCommand(auctionId));
        if (optionalAuction.isPresent()) {
            var auction = optionalAuction.get();
            response.header("Content-type", "application/json");
            response.status(OK_200);
            return new JSONObject()
                    .put("item", new JSONObject()
                            .put("name", auction.item.name)
                            .put("description", auction.item.description)
                    )
                    .put("initial_bid", auction.initialBid.amount)
                    .put("conquer_price", auction.conquerPrice.amount)
                    .put("expiration_date", auction.expirationDate.toString())
                    .put("minimum_overbidding_price", auction.minimumOverbiddingPrice.amount)
                    .toString();
        }
        response.status(NOT_FOUND_404);
        return "An auction with that id does not exists.";
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

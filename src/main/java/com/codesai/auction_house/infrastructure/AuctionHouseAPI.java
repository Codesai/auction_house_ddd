package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.actions.commands.CreateAuctionCommand;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.AuctionException;
import com.codesai.auction_house.business.model.auction.exceptions.AuctionNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.function.Supplier;

import static com.codesai.auction_house.infrastructure.ActionFactory.*;
import static java.util.stream.Collectors.toList;
import static org.eclipse.jetty.http.HttpStatus.*;


public class AuctionHouseAPI {
    private final Request request;
    private final Response response;

    public AuctionHouseAPI(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Object createAuction() throws JSONException {
        return eval(() -> {
            var command = createAuctionCommandFrom(request.body());
            var auctionId = createAuctionAction().execute(command);
            response.status(CREATED_201);
            response.header("Location", request.url() + "/" + auctionId);
            return auctionId;
        });
    }

    public Object bidAuction() throws JSONException {
        return eval(() -> {
            bidAuctionAction().execute(createBidAuctionCommand());
            return createdOk();
        });
    }

    public Object retrieveAuction() throws JSONException {
        return eval(() -> {
            var auction = retrieveAuctionAction()
                    .execute(new RetrieveAuctionCommand(auction()));
            response.header("Content-type", "application/json");
            response.status(OK_200);
            return createAuctionJsonFrom(auction);
        });
    }

    public Object conquerAuction() throws JSONException {
        return eval(() -> {
            conquerAuctionAction().execute(createConquerAuctionCommand());
            response.status(OK_200);
            return "OK";
        });
    }

    private ConquerAuctionActionCommand createConquerAuctionCommand() {
        var bodyAsJson = new Gson().fromJson(request.body(), JsonObject.class);
        return new ConquerAuctionActionCommand(
                bodyAsJson.get("conqueror_id").getAsString(),
                request.params("auction_id")
        );
    }

    public Object eval(Supplier<Object> s) throws JSONException {
        try {
            return s.get();
        } catch (AuctionNotFoundException e) {
            return anAuctionNotFoundException();
       } catch (AuctionException e) {
            return anAuctionException(e);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return anMalformedRequestException();
        }  catch (Exception e) {
            return anUnknownError(e);
        }
    }

    private String anAuctionNotFoundException() {
        response.status(NOT_FOUND_404);
        return "An auction with that id does not exists.";
    }

    private String anMalformedRequestException() {
        response.status(400);
        return "The auction body is not well formed.";
    }

    private BidAuctionCommand createBidAuctionCommand() {
        return new BidAuctionCommand(auction(), amount(), bidderId());
    }

    private String bidderId() {
        var body = request.body();
        var bodyAsJson = new Gson().fromJson(body, JsonObject.class);
        return bodyAsJson.get("bidder_id").getAsString();
    }

    private Object createdOk() {
        response.status(CREATED_201);
        return "OK";
    }

    private String auction() {
        return request.params("id");
    }

    private double amount() {
        var body = request.body();
        var bodyAsJson = new Gson().fromJson(body, JsonObject.class);
        return bodyAsJson.get("amount").getAsDouble();
    }

    private String anAuctionException(Exception e) throws JSONException {
        response.status(422);
        return aJsonErrorException(e);
    }

    private String anUnknownError(Exception e) throws JSONException {
        e.printStackTrace();
        response.status(500);
        return aJsonErrorException(e);
    }

    private String aJsonErrorException(Exception e) throws JSONException {
        response.type("application/json");
        return new JSONObject()
                .put("name", e.getClass().getSimpleName())
                .put("description", e.getMessage())
                .toString();
    }

    private static String createAuctionJsonFrom(Auction auction) {
        try {
            return new JSONObject()
                    .put("item", new JSONObject()
                            .put("name", auction.item.name)
                            .put("description", auction.item.description)
                    )
                    .put("initial_bid", auction.startingPrice.amount)
                    .put("conquer_price", auction.conquerPrice.amount)
                    .put("bids", auction.bids.stream().map(AuctionHouseAPI::createBidJson).collect(toList()))
                    .put("expiration_date", auction.expirationDate.toString())
                    .put("minimum_overbidding_price", auction.minimumOverbiddingPrice.amount)
                    .put("owner_id", auction.ownerId.id)
                    .toString();
        } catch (JSONException e) {
           throw new RuntimeException(e);
        }
    }

    private static String createBidJson(Bid bid) {
        try {
            return new JSONObject()
                        .put("id", bid.id)
                        .put("amount", bid.money.amount)
                        .put("bidder_id", bid.bidderId.id)
                        .toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static CreateAuctionCommand createAuctionCommandFrom(String body) {
        try {
            var json = new Gson().fromJson(body, JsonObject.class);
            return new CreateAuctionCommand(
                    json.get("item").getAsJsonObject().get("name").getAsString(),
                    json.get("item").getAsJsonObject().get("description").getAsString(),
                    json.get("initial_bid").getAsDouble(),
                    json.get("conquer_price").getAsDouble(),
                    LocalDate.parse(json.get("expiration_date").getAsString()),
                    json.get("minimum_overbidding_price").getAsDouble(),
                    json.get("owner_id").getAsString()
            );
        } catch (Exception e) {
            throw new JsonSyntaxException(e);
        }
    }
}

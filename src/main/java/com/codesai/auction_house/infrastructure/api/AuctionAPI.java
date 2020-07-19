package com.codesai.auction_house.infrastructure.api;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.infrastructure.ActionFactory;
import com.codesai.auction_house.infrastructure.api.dtos.AuctionDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static org.eclipse.jetty.http.HttpStatus.*;

public class AuctionAPI {
    public static Object createAuction(Request request, Response response) throws JSONException {
        try {
            var command = parseAuctionCommand(request);
            var auctionId = ActionFactory.createAuctionAction().execute(command);
            response.status(CREATED_201);
            response.header("Location", request.url() + "/auction/" + auctionId);
        } catch (JsonSyntaxException e) {
            response.status(BAD_REQUEST_400);
            return "The auction body is not well formed.";
        } catch (RuntimeException e) {
            response.status(UNPROCESSABLE_ENTITY_422);
            response.type("application/json");
            return new JSONObject()
                            .put("name", e.getClass().getSimpleName())
                            .put("description", e.getMessage())
                            .toString();
        }
        return "";
    }

    private static CreateAuctionCommand parseAuctionCommand(Request request) {
        try {
            var auctionDTO = new Gson().fromJson(request.body(), AuctionDTO.class);
            return new CreateAuctionCommand(
                    auctionDTO.item.name,
                    auctionDTO.item.description,
                    auctionDTO.initial_bid,
                    auctionDTO.conquer_price,
                    LocalDate.parse(auctionDTO.expiration_date),
                    auctionDTO.owner_id
            );
        } catch (Exception e) {
            throw new JsonSyntaxException(e);
        }

    }
}

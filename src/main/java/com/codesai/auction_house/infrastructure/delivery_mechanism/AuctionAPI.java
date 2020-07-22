package com.codesai.auction_house.infrastructure.delivery_mechanism;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.business.model.exceptions.InitialBidCannotBeGreaterThanConquerPriceException;
import com.codesai.auction_house.business.model.generic.InvariantDTO;
import com.codesai.auction_house.infrastructure.ActionFactory;
import com.codesai.auction_house.infrastructure.api.dtos.AuctionDTO;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static org.eclipse.jetty.http.HttpStatus.*;

public class AuctionAPI {
    public static Object createAuction(Request request, Response response) {
        try {
            var auctionId = ActionFactory.createAuctionAction().execute(createAuctionCommand(request));
            response.status(CREATED_201);
            response.header("Location", request.url() + "/auction/" + auctionId);
            return "";
        } catch (JsonParseException exception) {
            response.status(BAD_REQUEST_400);
            return "The auction body is not well formed.";
        } catch (InitialBidCannotBeGreaterThanConquerPriceException exception) {
            response.status(UNPROCESSABLE_ENTITY_422);
            response.type("application/json");
            return new Gson().toJson(new InvariantDTO(
                    exception.getClass().getSimpleName(),
                    exception.getMessage()
            ));
        }

    }

    private static CreateAuctionCommand createAuctionCommand(Request request) {
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
        } catch (Exception exception) {
            throw new JsonParseException(exception);
        }

    }
}

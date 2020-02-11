package com.codesai.auction_house.business.model.auction.exceptions;

public class AuctionNotFoundException extends AuctionException {
    public AuctionNotFoundException(String message) {
        super(message);
    }
}

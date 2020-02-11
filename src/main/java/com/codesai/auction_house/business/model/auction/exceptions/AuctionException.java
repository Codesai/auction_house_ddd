package com.codesai.auction_house.business.model.auction.exceptions;

public abstract class AuctionException extends RuntimeException {
    public AuctionException(String message) {
        super(message);
    }
}

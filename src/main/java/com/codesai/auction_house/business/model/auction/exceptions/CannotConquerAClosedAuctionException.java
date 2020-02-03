package com.codesai.auction_house.business.model.auction.exceptions;

public class CannotConquerAClosedAuctionException extends AuctionException {
    public CannotConquerAClosedAuctionException() {
        super("Cannot conquer a closed auction");
    }
}

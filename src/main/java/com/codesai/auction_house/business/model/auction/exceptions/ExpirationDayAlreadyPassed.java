package com.codesai.auction_house.business.model.auction.exceptions;

public class ExpirationDayAlreadyPassed extends AuctionException {
    public ExpirationDayAlreadyPassed() {
        super("expiration date already passed");
    }
}

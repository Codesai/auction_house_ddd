package com.codesai.auction_house.business.model.auction.exceptions;

import com.codesai.auction_house.business.model.generic.Money;

public class FirstBidShouldBeGreaterThanStartingPrice extends AuctionException {
    public FirstBidShouldBeGreaterThanStartingPrice(Money startingPrice, Money bid) {
        super(String.format("Initial auction price is %.2f and bid is only %.2f", startingPrice.amount, bid.amount));
    }
}

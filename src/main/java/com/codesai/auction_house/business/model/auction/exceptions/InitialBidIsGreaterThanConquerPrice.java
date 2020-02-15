package com.codesai.auction_house.business.model.auction.exceptions;

import com.codesai.auction_house.business.model.generic.Money;

import java.util.Locale;

public class InitialBidIsGreaterThanConquerPrice extends AuctionException {
    public InitialBidIsGreaterThanConquerPrice(Money startingPrice, Money conquerPrice) {
        super(String.format(Locale.UK, "initial cannot be greater %.2f than conquer price %.2f", startingPrice.amount, conquerPrice.amount));
    }
}

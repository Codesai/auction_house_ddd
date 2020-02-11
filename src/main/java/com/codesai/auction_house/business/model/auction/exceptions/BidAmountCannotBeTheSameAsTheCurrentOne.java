package com.codesai.auction_house.business.model.auction.exceptions;

import com.codesai.auction_house.business.model.auction.Bid;

public class BidAmountCannotBeTheSameAsTheCurrentOne extends AuctionException {
    public BidAmountCannotBeTheSameAsTheCurrentOne(Bid bid) {
        super(String.format("Current top bid is %.2fs", bid.money.amount));
    }
}

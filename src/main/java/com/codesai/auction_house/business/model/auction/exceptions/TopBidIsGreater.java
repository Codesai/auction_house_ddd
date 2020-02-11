package com.codesai.auction_house.business.model.auction.exceptions;

import com.codesai.auction_house.business.model.auction.Bid;

public class TopBidIsGreater extends AuctionException {
    public TopBidIsGreater(Bid currentBid, Bid bidAttempt) {
        super(String.format("Current bid is: %.2f and the current attempt is only %.2f", currentBid.money.amount, bidAttempt.money.amount));
    }
}

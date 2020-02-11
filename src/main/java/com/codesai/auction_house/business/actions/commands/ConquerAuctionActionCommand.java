package com.codesai.auction_house.business.actions.commands;

import com.codesai.auction_house.business.model.bidder.BidderId;

public class ConquerAuctionActionCommand {
    public final BidderId userId;
    public final String auctionId;

    public ConquerAuctionActionCommand(String userId, String auctionId) {
        this.auctionId = auctionId;
        this.userId = new BidderId(userId);
    }
}

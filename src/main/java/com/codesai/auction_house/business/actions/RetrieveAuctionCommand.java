package com.codesai.auction_house.business.actions;

public class RetrieveAuctionCommand {
    public final String auctionId;

    public RetrieveAuctionCommand(String auctionId) {
        this.auctionId = auctionId;
    }
}

package com.codesai.auction_house.business.actions.commands;

public class ConquerAuctionActionCommand {
    public final String userId;
    public final String auctionId;

    public ConquerAuctionActionCommand(String userId, String auctionId) {
        this.auctionId = auctionId;
        this.userId = userId;
    }
}

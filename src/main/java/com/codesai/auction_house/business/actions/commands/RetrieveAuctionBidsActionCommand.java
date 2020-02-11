package com.codesai.auction_house.business.actions.commands;

public class RetrieveAuctionBidsActionCommand {
    public final String auctionId;

    public RetrieveAuctionBidsActionCommand(String auctionId) {
        this.auctionId = auctionId;
    }
}

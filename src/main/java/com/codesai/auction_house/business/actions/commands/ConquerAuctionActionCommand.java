package com.codesai.auction_house.business.actions.commands;

import com.codesai.auction_house.business.model.bidder.BidderId;

public class ConquerAuctionActionCommand {
    public final BidderId conquerorId;
    public final String auctionId;

    public ConquerAuctionActionCommand(String conquerorId, String auctionId) {
        this.auctionId = auctionId;
        this.conquerorId = new BidderId(conquerorId);
    }
}

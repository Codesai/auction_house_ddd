package com.codesai.auction_house.business.actions.commands;

import com.codesai.auction_house.business.model.generic.Money;

import static com.codesai.auction_house.business.model.generic.Money.money;

public class BidAuctionCommand {
    public final String auctionId;
    public final Money biddingAmount;

    public BidAuctionCommand(String auctionId, double bidAmount) {
        this.auctionId = auctionId;
        this.biddingAmount = money(bidAmount);
    }
}

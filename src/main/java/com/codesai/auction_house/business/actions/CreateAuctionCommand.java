package com.codesai.auction_house.business.actions;

import java.time.LocalDate;

public class CreateAuctionCommand {
    final String name;
    final String description;
    final double initialBid;
    final double conquerPrice;
    final LocalDate expirationDate;
    final double minimumOverbiddingPrice;

    public CreateAuctionCommand(String name, String description, double initialBid, double conquerPrice, LocalDate expirationDate, double minimumOverbiddingPrice) {
        this.name = name;
        this.description = description;
        this.initialBid = initialBid;
        this.conquerPrice = conquerPrice;
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
    }
}

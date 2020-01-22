package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.generic.Money;

import java.time.LocalDate;

import static com.codesai.auction_house.business.generic.Money.money;

public class CreateAuctionCommand {
    final String name;
    final String description;
    final Money initialBid;
    final Money conquerPrice;
    final LocalDate expirationDate;
    final Money minimumOverbiddingPrice;

    public CreateAuctionCommand(String name,
                                String description,
                                double initialBid,
                                double conquerPrice,
                                LocalDate expirationDate,
                                double minimumOverbiddingPrice) {
        this.name = name;
        this.description = description;
        this.initialBid = money(initialBid);
        this.conquerPrice = money(conquerPrice);
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = money(minimumOverbiddingPrice);
    }
}

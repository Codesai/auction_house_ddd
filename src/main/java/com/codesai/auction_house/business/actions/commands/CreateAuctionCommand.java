package com.codesai.auction_house.business.actions.commands;

import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

import static com.codesai.auction_house.business.model.generic.Money.money;

public class CreateAuctionCommand {
    public final String name;
    public final String description;
    public final Money startingPrice;
    public final Money conquerPrice;
    public final LocalDate expirationDate;
    public final Money minimumOverbiddingPrice;
    public final OwnerId ownerId;

    public CreateAuctionCommand(String name,
                                String description,
                                double startingPrice,
                                double conquerPrice,
                                LocalDate expirationDate,
                                double minimumOverbiddingPrice,
                                String ownerId) {
        this.name = name;
        this.description = description;
        this.startingPrice = money(startingPrice);
        this.conquerPrice = money(conquerPrice);
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = money(minimumOverbiddingPrice);
        this.ownerId = new OwnerId(ownerId);
    }

}

package com.codesai.auction_house.business.model.owner;

import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Item;
import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

public class Owner {
    private final OwnerId id;

    public Owner(OwnerId id) {
        this.id = id;
    }

    public Auction auctionWith(Item item, Money startingPrice, Money conquerPrice, LocalDate expirationDate, Money minimumOverbiddingPrice) {
        return new Auction(
                item,
                startingPrice,
                conquerPrice,
                expirationDate,
                minimumOverbiddingPrice,
                id);
    }
}

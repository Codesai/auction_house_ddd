package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

public class Auction {
    public String id;
    public String name;
    public String description;
    public Money initialBid;
    public Money conquerPrice;
    public LocalDate expirationDay;
    public String ownerId;

    public Auction(String id, String name, String description, Money initialBid, Money conquerPrice, LocalDate expirationDay, String ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initialBid = initialBid;
        this.conquerPrice = conquerPrice;
        this.expirationDay = expirationDay;
        this.ownerId = ownerId;
    }

}

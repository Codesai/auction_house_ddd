package com.codesai.auction_house.business;

import com.codesai.auction_house.business.auction.Item;
import com.codesai.auction_house.business.generic.Money;

import java.time.LocalDate;
import java.util.UUID;

public class Auction {
    public String id;
    public Item item;
    public Money initialBid;
    public Money conquerPrice;
    public LocalDate expirationDate;
    public Money minimumOverbiddingPrice;

    public Auction(Item item, Money initialBid, Money conquerPrice, LocalDate expirationDate, Money minimumOverbiddingPrice) {
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.initialBid = initialBid;
        this.conquerPrice = conquerPrice;
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
    }
}

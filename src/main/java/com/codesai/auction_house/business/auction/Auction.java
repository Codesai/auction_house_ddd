package com.codesai.auction_house.business.auction;

import com.codesai.auction_house.business.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

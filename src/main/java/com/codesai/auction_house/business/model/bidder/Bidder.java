package com.codesai.auction_house.business.model.bidder;

import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.generic.Money;

public class Bidder {
    public final BidderId id;

    public Bidder(BidderId id) {
        this.id = id;
    }

    public Bid bidWith(Money money) {
        return new Bid(money, id);
    }
}

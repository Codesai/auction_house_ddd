package com.codesai.auction_house.business.model.bidder;

import java.util.HashMap;
import java.util.Map;

public class BidderRepository {
    private Map<BidderId, Bidder> map = new HashMap<>();

    public void save(Bidder bidder) {
        this.map.put(bidder.id, bidder);
    }
}

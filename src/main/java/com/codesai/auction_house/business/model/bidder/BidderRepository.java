package com.codesai.auction_house.business.model.bidder;

public interface BidderRepository {
    Bidder retrieveById(BidderId id);

    void save(Bidder bidder);
}

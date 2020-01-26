package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.exceptions.AcutionNotFoundException;

public interface AuctionRepository {
    void save(Auction auction);

    Auction retrieveById(String id) throws AcutionNotFoundException;
}

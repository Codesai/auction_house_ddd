package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.exceptions.AuctionNotFoundException;

import java.util.List;

public interface AuctionRepository {
    void save(Auction auction);

    Auction retrieveById(String id) throws AuctionNotFoundException;

    List<Auction> retrieveAll();
}

package com.codesai.auction_house.business.auction;

import java.util.Optional;

public interface AuctionRepository {
    void save(Auction auction);

    Optional<Auction> retrieveById(String id);
}

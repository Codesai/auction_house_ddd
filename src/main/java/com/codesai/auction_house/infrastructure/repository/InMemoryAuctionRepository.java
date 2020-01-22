package com.codesai.auction_house.infrastructure.repository;

import com.codesai.auction_house.business.auction.Auction;
import com.codesai.auction_house.business.auction.AuctionRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAuctionRepository implements AuctionRepository {
    private Map<String, Auction> auctions = new HashMap<>();

    @Override
    public void save(Auction auction) {
        auctions.put(auction.id, auction);
    }

    @Override
    public Optional<Auction> retrieveById(String id) {
        return Optional.of(auctions.get(id));
    }

    public void clean() {
        auctions = new HashMap<>();
    }
}

package com.codesai.auction_house.infrastructure.repository;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.AuctionNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAuctionRepository implements AuctionRepository {
    private Map<String, Auction> auctions = new HashMap<>();

    @Override
    public void save(Auction auction) {
        auctions.put(auction.id, auction);
    }

    @Override
    public Auction retrieveById(String id) {
        if (!auctions.containsKey(id)) throw new AuctionNotFoundException("An auction with that id does not exists.");
        return auctions.get(id);
    }

    @Override
    public List<Auction> retrieveAll() {
        return List.copyOf(auctions.values());
    }

    public void clean() {
        auctions = new HashMap<>();
    }
}

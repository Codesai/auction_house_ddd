package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.auction.Auction;
import com.codesai.auction_house.business.auction.AuctionRepository;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    public static CreateAuctionAction createAuction() {
        return new CreateAuctionAction(new InMemoryAuctionRepository());
    }

    private static class InMemoryAuctionRepository implements AuctionRepository {
        private Map<String, Auction> auctions = new HashMap<>();

        @Override
        public void save(Auction auction) {
            auctions.put(auction.id, auction);
        }
    }
}

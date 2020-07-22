package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.model.Auction;
import com.codesai.auction_house.business.model.AuctionRepository;

import java.util.HashMap;

public class ActionFactory {
    public static CreateAuctionAction createAuctionAction() {
        return new CreateAuctionAction(new AuctionRepository() {
            private final HashMap<String, Auction> auctions = new HashMap<>();

            @Override
            public void save(Auction auction) {
                auctions.put(auction.id, auction);
            }
        });
    }
}

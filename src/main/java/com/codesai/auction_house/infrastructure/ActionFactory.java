package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.actions.RetrieveAuctionAction;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;

public class ActionFactory {

    private static final InMemoryAuctionRepository repository = new InMemoryAuctionRepository();

    public static CreateAuctionAction createAuction() {
        return new CreateAuctionAction(auctionRepository());
    }

    public static InMemoryAuctionRepository auctionRepository() {
        return repository;
    }

    public static RetrieveAuctionAction retrieveAuctionAction() {
        return new RetrieveAuctionAction(auctionRepository());
    }
}

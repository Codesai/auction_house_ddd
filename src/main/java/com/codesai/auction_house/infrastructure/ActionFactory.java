package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.actions.RetrieveAuctionAction;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;
import org.apache.commons.lang3.NotImplementedException;

public class ActionFactory {
    public static CreateAuctionAction createAuction() {
        return new CreateAuctionAction(auctionRepository());
    }

    public static InMemoryAuctionRepository auctionRepository() {
        return new InMemoryAuctionRepository();
    }

    public static RetrieveAuctionAction retrieveAuctionAction() {
        throw new NotImplementedException("Retrieve Auction is not implemented");
    }
}

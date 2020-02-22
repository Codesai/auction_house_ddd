package com.codesai.auction_house.infrastructure.delivery_mechanism;

import com.codesai.auction_house.business.actions.BidAuctionAction;
import com.codesai.auction_house.business.actions.ConquerAuctionAction;
import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.actions.RetrieveAuctionAction;
import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.bidder.Bidder;
import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.bidder.BidderRepository;
import com.codesai.auction_house.business.model.owner.Owner;
import com.codesai.auction_house.business.model.owner.OwnerRepository;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;

public class ActionFactory {

    private static final InMemoryAuctionRepository repository = new InMemoryAuctionRepository();

    public static CreateAuctionAction createAuctionAction() {
        return new CreateAuctionAction(auctionRepository(), ownerRepository());
    }

    private static OwnerRepository ownerRepository() {
        return new OwnerRepository() {
            @Override
            public Owner retrieveById(OwnerId ownerId) {
                return new Owner(ownerId);
            }

            @Override
            public void save() {

            }
        };
    }

    public static BidAuctionAction bidAuctionAction() { return new BidAuctionAction(auctionRepository(), bidderRepository()); }

    private static BidderRepository bidderRepository() {
        return new BidderRepository() {
            @Override
            public Bidder retrieveById(BidderId id) {
                return new Bidder(id);
            }

            @Override
            public void save() {

            }
        };
    }

    public static InMemoryAuctionRepository auctionRepository() {
        return repository;
    }

    public static RetrieveAuctionAction retrieveAuctionAction() {
        return new RetrieveAuctionAction(auctionRepository());
    }

    public static ConquerAuctionAction conquerAuctionAction() {
        return new ConquerAuctionAction(repository, bidderRepository());
    }
}

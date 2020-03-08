package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.*;
import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.bidder.Bidder;
import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.bidder.BidderRepository;
import com.codesai.auction_house.business.model.generic.Calendar;
import com.codesai.auction_house.business.model.owner.Owner;
import com.codesai.auction_house.business.model.owner.OwnerRepository;
import com.codesai.auction_house.infrastructure.events.InMemoryEventProducer;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;

import java.time.LocalDate;

public class ActionFactory {

    private static final InMemoryAuctionRepository repository = new InMemoryAuctionRepository();

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

    public static Calendar calendar() {
        return new Calendar() {
            @Override
            public LocalDate yesterday() {
                return LocalDate.now();
            }
        };
    }

    public static CreateAuctionAction createAuctionAction() {
        return new CreateAuctionAction(auctionRepository(), ownerRepository());
    }

    public static RetrieveAuctionAction retrieveAuctionAction() {
        return new RetrieveAuctionAction(auctionRepository());
    }

    public static ConquerAuctionAction conquerAuctionAction() {
        return new ConquerAuctionAction(repository, bidderRepository());
    }

    public static DeclareAuctionWinnerAction declareAuctionWinnerAuction() {
        return new DeclareAuctionWinnerAction(auctionRepository(), calendar(), new InMemoryEventProducer());
    }
}

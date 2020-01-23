package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;

public class BidAuctionAction {
    private AuctionRepository repository;

    public BidAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(BidAuctionCommand command) {
        this.repository.retrieveById(command.auctionId).ifPresent(auction -> {
            auction.bid(new Bid(command.bidMoney));
            repository.save(auction);
        });

    }
}

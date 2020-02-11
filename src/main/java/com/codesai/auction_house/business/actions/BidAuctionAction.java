package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.FirstBidShouldBeGreaterThanStartingPrice;

public class BidAuctionAction {
    private AuctionRepository repository;

    public BidAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(BidAuctionCommand command) throws FirstBidShouldBeGreaterThanStartingPrice {
        var auction = repository.retrieveById(command.auctionId);
        auction.bid(new Bid(command.biddingAmount, command.bidderId));
        repository.save(auction);
    }
}

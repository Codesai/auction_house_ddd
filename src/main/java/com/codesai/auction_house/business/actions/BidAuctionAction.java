package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.BidAmountCannotBeTheSameAsTheCurrentOne;
import com.codesai.auction_house.business.model.auction.exceptions.CurrentBidIsGreater;

public class BidAuctionAction {
    private AuctionRepository repository;

    public BidAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(BidAuctionCommand command) {
        this.repository.retrieveById(command.auctionId).ifPresent(auction -> {
            if (command.biddingAmount.isLessThan(auction.currentBid().money)) throw new CurrentBidIsGreater();
            if (command.biddingAmount.equals(auction.currentBid().money)) throw new BidAmountCannotBeTheSameAsTheCurrentOne();
            auction.bid(new Bid(command.biddingAmount));
            repository.save(auction);
        });
    }
}

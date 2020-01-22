package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.RetrieveAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;

import java.util.Optional;

public class RetrieveAuctionAction {
    private AuctionRepository repository;

    public RetrieveAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public Optional<Auction> execute(RetrieveAuctionCommand command) {
        return this.repository.retrieveById(command.auctionId);
    }
}

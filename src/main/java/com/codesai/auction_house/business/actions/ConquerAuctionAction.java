package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.CannotConquerAClosedAuctionException;

public class ConquerAuctionAction {
    private final AuctionRepository repository;

    public ConquerAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void  execute(ConquerAuctionActionCommand command) throws CannotConquerAClosedAuctionException {
        Auction auction = repository.retrieveById(command.auctionId);
        // TODO: What can we do to make the Bidder to conquer the Auction through a bidder.conquer
        auction.conquerBy(command.conquerorId);
        repository.save(auction);
    }
}

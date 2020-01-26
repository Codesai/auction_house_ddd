package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.CannotConquerAClosedAuctionException;

public class ConquerAuctionAction {
    private final AuctionRepository repository;

    public ConquerAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(ConquerAuctionActionCommand command) throws CannotConquerAClosedAuctionException {
        var auction = repository.retrieveById(command.auctionId);
        if (auction.get().isClosed()) throw new CannotConquerAClosedAuctionException();
        auction.get().conquerBy(command.userId);
        repository.save(auction.get());
    }
}

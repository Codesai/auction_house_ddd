package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;

public class ConquerAuctionAction {
    private final AuctionRepository repository;

    public ConquerAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(ConquerAuctionActionCommand command) {
        var auction = repository.retrieveById(command.auctionId);
        auction.get().conquerBy(command.userId);
        repository.save(auction.get());
    }
}

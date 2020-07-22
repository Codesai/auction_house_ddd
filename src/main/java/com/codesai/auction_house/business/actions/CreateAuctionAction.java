package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.AuctionRepository;

import static com.codesai.auction_house.business.model.Auction.anAuction;

public class CreateAuctionAction {
    private final AuctionRepository repository;

    public CreateAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public String execute(CreateAuctionCommand command) {
        var auction = anAuction(command.name,
                command.description,
                command.initialBidAmount,
                command.conquerPriceAmount,
                command.expirationDay,
                command.ownerId);
        repository.save(auction);
        return auction.id;
    }
}

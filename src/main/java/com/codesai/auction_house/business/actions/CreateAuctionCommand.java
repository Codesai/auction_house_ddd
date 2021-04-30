package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.CreateAuctionRequest;
import com.codesai.auction_house.business.model.auction.repository.AuctionRepository;

import java.util.UUID;

public class CreateAuctionCommand {
    private final AuctionRepository repository;

    public CreateAuctionCommand(AuctionRepository repository) {
        this.repository = repository;
    }

    public String execute(CreateAuctionRequest request) {
        var auction = new Auction(UUID.randomUUID().toString(),
                request.name,
                request.description,
                request.initialBidAmount,
                request.conquerPriceAmount,
                request.expirationDay,
                request.ownerId);
        repository.save(auction);
        return auction.id;
    }
}

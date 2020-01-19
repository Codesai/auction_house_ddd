package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.Auction;
import com.codesai.auction_house.business.AuctionRepository;
import com.codesai.auction_house.business.auction.Item;

import java.util.UUID;

import static com.codesai.auction_house.business.generic.Money.money;

public class CreateAuctionAction {
    private AuctionRepository repository;

    public CreateAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public UUID execute(CreateAuctionCommand command) {
        final var auction = new Auction(
                new Item(command.name, command.description),
                money(command.initialBid),
                money(command.conquerPrice),
                command.expirationDate,
                money(command.minimumOverbiddingPrice)
        );
        repository.save(auction);
        return UUID.randomUUID();
    }
}

package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.CreateAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;

import static com.codesai.auction_house.business.model.auction.Item.item;

public class CreateAuctionAction {
    private AuctionRepository repository;

    public CreateAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public String execute(CreateAuctionCommand command) {
        var auction = new Auction(item(command.name, command.description), new Bid(command.startingPrice), command.conquerPrice, command.expirationDate, command.minimumOverbiddingPrice);
        repository.save(auction);
        return auction.id;
    }
}

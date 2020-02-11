package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.CreateAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.owner.OwnerRepository;

import static com.codesai.auction_house.business.model.auction.Item.item;

public class CreateAuctionAction {
    private AuctionRepository auctionRepository;
    private final OwnerRepository ownerRepository;

    public CreateAuctionAction(AuctionRepository auctionRepository, OwnerRepository ownerRepository) {
        this.auctionRepository = auctionRepository;
        this.ownerRepository = ownerRepository;
    }

    public String execute(CreateAuctionCommand command) {
        var owner = ownerRepository.retrieveById(command.ownerId);
        var auction = owner
                .auctionWith(
                        item(command.name, command.description),
                        command.startingPrice,
                        command.conquerPrice,
                        command.expirationDate,
                        command.minimumOverbiddingPrice
                );
        auctionRepository.save(auction);
        ownerRepository.save(owner);
        return auction.id;
    }
}

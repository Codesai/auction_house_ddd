package com.codesai.auction_house.business.actions.commands;

import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;

import java.util.List;

public class RetrieveAuctionBidsAction {
    private AuctionRepository repository;

    public RetrieveAuctionBidsAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public List<Bid> execute(RetrieveAuctionBidsActionCommand command) {
        return repository.retrieveById(command.auctionId)
                .map(value -> value.bids)
                .orElse(null);
    }
}

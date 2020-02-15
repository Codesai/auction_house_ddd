package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.CannotConquerAClosedAuctionException;
import com.codesai.auction_house.business.model.bidder.Bidder;
import com.codesai.auction_house.business.model.bidder.BidderRepository;

public class ConquerAuctionAction {
    private final AuctionRepository auctions;
    private final BidderRepository bidders;

    public ConquerAuctionAction(AuctionRepository auctions, BidderRepository bidders) {
        this.auctions = auctions;
        this.bidders = bidders;
    }

    public void  execute(ConquerAuctionActionCommand command) throws CannotConquerAClosedAuctionException {
        Auction auction = auctions.retrieveById(command.auctionId);
        Bidder conqueror = bidders.retrieveById(command.conquerorId);
        auction.conquerBy(conqueror);
        auctions.save(auction);
    }
}

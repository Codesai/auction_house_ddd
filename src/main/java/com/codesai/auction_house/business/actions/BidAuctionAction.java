package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.FirstBidShouldBeGreaterThanStartingPrice;
import com.codesai.auction_house.business.model.bidder.BidderRepository;

public class BidAuctionAction {
    private final AuctionRepository auctionRepository;
    private final BidderRepository bidderRepository;

    public BidAuctionAction(AuctionRepository auctionRepository, BidderRepository bidderRepository) {
        this.auctionRepository = auctionRepository;
        this.bidderRepository = bidderRepository;
    }

    public void execute(BidAuctionCommand command) throws FirstBidShouldBeGreaterThanStartingPrice {
        var auction = auctionRepository.retrieveById(command.auctionId);
        var bidder = bidderRepository.retrieveById(command.bidderId);
        var bid = bidder.bidWith(command.biddingAmount);
        auction.proposeBid(bid);
        auctionRepository.save(auction);
        bidderRepository.save();
    }
}

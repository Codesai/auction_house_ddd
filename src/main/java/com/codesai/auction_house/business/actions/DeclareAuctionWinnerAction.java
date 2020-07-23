package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.EventProducer;
import com.codesai.auction_house.business.model.auction.services.AuctionService;
import com.codesai.auction_house.business.model.generic.Calendar;

public class DeclareAuctionWinnerAction {
    private final AuctionRepository auctionRepository;
    private final Calendar calendar;
    private final EventProducer eventProducer;

    public DeclareAuctionWinnerAction(AuctionRepository auctionRepository, Calendar calendar, EventProducer eventProducer) {
        this.auctionRepository = auctionRepository;
        this.calendar = calendar;
        this.eventProducer = eventProducer;
    }

    public void execute() {
        new AuctionService(auctionRepository, eventProducer, calendar).declareAuctionWinner();
    }

}

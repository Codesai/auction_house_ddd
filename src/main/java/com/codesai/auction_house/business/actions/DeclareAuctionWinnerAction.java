package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.EventProducer;
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
        auctionRepository.retrieveAll().stream()
                .filter(auction -> auction.isPendingWinnerDeclaration(calendar))
                .forEach(this::declareWinner);
    }



    private void declareWinner(Auction auction) {
        var declareWinnerEvent = auction.winnerDeclared();
        auctionRepository.save(auction);
        eventProducer.produce(declareWinnerEvent);
    }
}

package com.codesai.auction_house.business.model.auction.services;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.EventProducer;
import com.codesai.auction_house.business.model.generic.Calendar;

public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final EventProducer eventProducer;
    private final Calendar calendar;

    public AuctionService(AuctionRepository auctionRepository, EventProducer eventProducer, Calendar calendar) {
        this.auctionRepository = auctionRepository;
        this.eventProducer = eventProducer;
        this.calendar = calendar;
    }

    public void declareAuctionWinner() {
        auctionRepository.retrieveAll().stream()
                .filter(auction -> auction.isPendingWinnerDeclaration(calendar))
                .forEach(this::declareWinner);
    }

    private void declareWinner(Auction auction) {
        final var declareWinnerEvent = auction.winnerDeclared();
        auctionRepository.save(auction);
        eventProducer.produce(declareWinnerEvent);
    }
}

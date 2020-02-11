package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.EventProducer;
import com.codesai.auction_house.business.model.auction.events.DeclareWinnerEvent;
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
                .filter(auction -> auction.expirationDate.equals(calendar.yesterday()))
                .filter(auction -> auction.topBid().isPresent())
                .forEach(auction -> eventProducer.produce(new DeclareWinnerEvent(
                    auction.topBid().get().bidderId,
                    auction.id,
                    auction.topBid().get().money
                )));
    }
}

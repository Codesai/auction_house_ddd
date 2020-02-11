package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.events.DeclareWinnerEvent;

public interface EventProducer {
    void produce(DeclareWinnerEvent declareWinnerEvent);
}

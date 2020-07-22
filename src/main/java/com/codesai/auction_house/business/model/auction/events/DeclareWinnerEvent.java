package com.codesai.auction_house.business.model.auction.events;

import com.codesai.auction_house.business.model.generic.Event;
import com.codesai.auction_house.business.model.generic.Money;

public class DeclareWinnerEvent extends Event {

    public final String winner;
    public final String auctionId;
    public final Money bidWinnerAmount;

    public DeclareWinnerEvent(String winner, String auctionId, Money bidWinnerAmount) {
        this.winner = winner;
        this.auctionId = auctionId;
        this.bidWinnerAmount = bidWinnerAmount;
    }
}

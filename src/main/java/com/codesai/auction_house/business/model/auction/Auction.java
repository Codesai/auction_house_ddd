package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayAlreadyPassed;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayIsTooFar;
import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.auction.exceptions.MinimumOverbiddingPriceIsNotAllowed;
import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;
import static java.util.Collections.singletonList;

public class Auction {
    private static final Money MINIMUM_MONEY_TO_OVERBID = money(1);

    public String id;
    public Item item;
    public Money initialBid;
    public Money conquerPrice;
    public LocalDate expirationDate;
    public Money minimumOverbiddingPrice;
    public List<Bid> bids;
    public AuctionState state = AuctionState.LIVE;

    public Auction(Item item, Bid initialBid, Money conquerPrice, LocalDate expirationDate, Money minimumOverbiddingPrice) {
        if (conquerPrice.isLessThan(initialBid.money)) throw new InitialBidIsGreaterThanConquerPrice();
        if (minimumOverbiddingPrice.isLessThan(MINIMUM_MONEY_TO_OVERBID)) throw new MinimumOverbiddingPriceIsNotAllowed();
        if (expirationDate.isAfter(now().plusWeeks(2))) throw new ExpirationDayIsTooFar();
        if (expirationDate.isBefore(now())) throw new ExpirationDayAlreadyPassed();
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.initialBid = initialBid.money;
        this.conquerPrice = conquerPrice;
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
        this.bids = new LinkedList<>(singletonList(initialBid));
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public void bid(Bid bid) {
        this.bids.add(0, bid);
    }

    public Bid currentBid() {
        return this.bids.get(0);
    }

    public void conquerBy(String userId) {
        expirationDate = LocalDate.now().minusDays(1);
        bids.add(0, new Bid(conquerPrice, userId));
    }

    public Optional<Bid> winner() {
        if (bids.isEmpty()) return Optional.empty();
        return Optional.of(bids.get(0));
    }
}

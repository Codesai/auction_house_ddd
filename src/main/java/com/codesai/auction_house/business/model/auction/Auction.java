package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.auction.exceptions.*;
import com.codesai.auction_house.business.model.bidder.BidderId;
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

public class Auction {
    private static final Money MINIMUM_MONEY_TO_OVERBID = money(1);

    public final OwnerId ownerId;
    public final String id;
    public final Item item;
    public final Money startingPrice;
    public final Money conquerPrice;
    public final Money minimumOverbiddingPrice;
    public final List<Bid> bids;
    public LocalDate expirationDate;

    public Auction(Item item, Money startingPrice, Money conquerPrice, LocalDate expirationDate, Money minimumOverbiddingPrice, OwnerId ownerId) {
        this.ownerId = ownerId;
        if (conquerPrice.isLessThan(startingPrice)) throw new InitialBidIsGreaterThanConquerPrice(startingPrice, conquerPrice);
        if (minimumOverbiddingPrice.isLessThan(MINIMUM_MONEY_TO_OVERBID))
            throw new MinimumOverbiddingPriceIsNotAllowed();
        if (expirationDate.isAfter(now().plusWeeks(2))) throw new ExpirationDayIsTooFar();
        if (expirationDate.isBefore(now())) throw new ExpirationDayAlreadyPassed();
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.startingPrice = startingPrice;
        this.conquerPrice = conquerPrice;
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
        this.bids = new LinkedList<>();
    }

    public void proposeBid(Bid bid) {
        topBid().ifPresentOrElse(
                currentBid -> {
                    if (currentBid.money.isGreaterThan(bid.money)) throw new TopBidIsGreater(currentBid, bid);
                    if (bid.money.equals(currentBid.money)) throw new BidAmountCannotBeTheSameAsTheCurrentOne(currentBid);
                    addBid(bid);
                }, ()  -> {
                    if (bid.money.isLessThan(startingPrice)) throw new FirstBidShouldBeGreaterThanStartingPrice(startingPrice, bid.money);
                    addBid(bid);
                });
    }

    private void addBid(Bid bid) {
        this.bids.add(0, bid);
    }

    public Optional<Bid> topBid() {
        if (!bids.isEmpty())  return Optional.of(bids.get(0));
        return Optional.empty();
    }

    public void conquerBy(BidderId bidderId) throws CannotConquerAClosedAuctionException {
        if (expirationDate.isBefore(now())) throw new CannotConquerAClosedAuctionException();
        expirationDate = LocalDate.now().minusDays(1);
        addBid(new Bid(conquerPrice, bidderId));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}

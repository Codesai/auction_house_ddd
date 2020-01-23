package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayAlreadyPassed;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayIsTooFar;
import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.auction.exceptions.MinimumOverbiddingPriceIsNotAllowed;
import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.UUID;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;

public class Auction {
    private static final Money MINIMUM_MONEY_TO_OVERBID = money(1);

    public String id;
    public Item item;
    public Money initialBid;
    public Money conquerPrice;
    public LocalDate expirationDate;
    public Money minimumOverbiddingPrice;

    public Auction(Item item, Money initialBid, Money conquerPrice, LocalDate expirationDate, Money minimumOverbiddingPrice) {
        if (conquerPrice.isLessThan(initialBid)) throw new InitialBidIsGreaterThanConquerPrice();
        if (minimumOverbiddingPrice.isLessThan(MINIMUM_MONEY_TO_OVERBID)) throw new MinimumOverbiddingPriceIsNotAllowed();
        if (expirationDate.isAfter(now().plusWeeks(2))) throw new ExpirationDayIsTooFar();
        if (expirationDate.isBefore(now())) throw new ExpirationDayAlreadyPassed();
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.initialBid = initialBid;
        this.conquerPrice = conquerPrice;
        this.expirationDate = expirationDate;
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

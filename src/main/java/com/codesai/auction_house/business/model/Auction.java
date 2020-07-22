package com.codesai.auction_house.business.model;

import com.codesai.auction_house.business.model.exceptions.ExpirationDayIsBeforeThanTodayException;
import com.codesai.auction_house.business.model.exceptions.InitialBidCannotBeGreaterThanConquerPriceException;
import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.UUID;

public class Auction {
    public final String id;
    public final String name;
    public final String description;
    public final Money initialBidAmount;
    public final Money conquerPriceAmount;
    public final LocalDate expirationDay;
    public final String ownerId;

    private Auction(String name, String description, Money initialBidAmount, Money conquerPriceAmount, LocalDate expirationDay, String ownerId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.initialBidAmount = initialBidAmount;
        this.conquerPriceAmount = conquerPriceAmount;
        this.expirationDay = expirationDay;
        this.ownerId = ownerId;
    }

    public static Auction anAuction(String name, String description, Money initialBidAmount, Money conquerPriceAmount, LocalDate expirationDay, String ownerId) {
        if (expirationDay.isBefore(LocalDate.now())) throw new ExpirationDayIsBeforeThanTodayException();
        if (initialBidAmount.isGreaterThan(conquerPriceAmount)) throw new InitialBidCannotBeGreaterThanConquerPriceException(initialBidAmount, conquerPriceAmount);
        return new Auction(name, description, initialBidAmount, conquerPriceAmount, expirationDay, ownerId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

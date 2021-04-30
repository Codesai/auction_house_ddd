package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

public class CreateAuctionRequest {
    public final String name;
    public final String description;
    public final Money initialBidAmount;
    public final Money conquerPriceAmount;
    public final LocalDate expirationDay;
    public final String ownerId;

    public CreateAuctionRequest(String name,
                                String description,
                                double initialBidAmount,
                                double conquerPriceAmount,
                                LocalDate expirationDay,
                                String ownerId) {
        this.name = name;
        this.description = description;
        this.initialBidAmount = new Money(initialBidAmount);
        this.conquerPriceAmount = new Money(conquerPriceAmount);
        this.expirationDay = expirationDay;
        this.ownerId = ownerId;
    }
}

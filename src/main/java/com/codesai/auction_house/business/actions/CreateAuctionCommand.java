package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

import static com.codesai.auction_house.business.model.generic.Money.money;

public class CreateAuctionCommand {
    public final String name;
    public final String description;
    public final Money initialBidAmount;
    public final Money conquerPriceAmount;
    public final LocalDate expirationDay;
    public final String ownerId;

    public CreateAuctionCommand(String name, String description, double initialBidAmount, double conquerPriceAmount, LocalDate expirationDay, String ownerId) {
        this.name = name;
        this.description = description;
        this.initialBidAmount = money(initialBidAmount);
        this.conquerPriceAmount = money(conquerPriceAmount);
        this.expirationDay = expirationDay;
        this.ownerId = ownerId;
    }
}

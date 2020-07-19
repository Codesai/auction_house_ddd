package com.codesai.auction_house.infrastructure.api.dtos;


import java.util.List;

public class AuctionDTO {
    public ItemDTO item;
    public double initial_bid;
    public double conquer_price;
    public List<String> bids;
    public String expiration_date;
    public String owner_id;

    public AuctionDTO(String name, String description, double initialBidAmount, double conquerPriceAmount, List<String> bids, String expirationDate, String ownerId) {
        this.item = new ItemDTO(name, description);
        this.initial_bid = initialBidAmount;
        this.conquer_price = conquerPriceAmount;
        this.bids = bids;
        this.expiration_date = expirationDate;
        this.owner_id = ownerId;
    }

}


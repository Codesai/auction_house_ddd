package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.auction.Auction;
import com.codesai.auction_house.business.auction.AuctionRepository;
import com.codesai.auction_house.business.auction.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.auction.MinimumOverbiddingPriceIsNotAllowed;
import com.codesai.auction_house.business.generic.Money;

import static com.codesai.auction_house.business.auction.Item.item;
import static com.codesai.auction_house.business.generic.Money.money;

public class CreateAuctionAction {
    private static final Money MINIMUM_MONEY_TO_OVERBID = money(1);
    private AuctionRepository repository;

    public CreateAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public String execute(CreateAuctionCommand command) {
        if (command.conquerPrice.isLessThan(command.initialBid)) throw new InitialBidIsGreaterThanConquerPrice();
        if (command.minimumOverbiddingPrice.isLessThan(MINIMUM_MONEY_TO_OVERBID)) throw new MinimumOverbiddingPriceIsNotAllowed();
        var auction = new Auction(
                item(command.name, command.description),
                command.initialBid,
                command.conquerPrice,
                command.expirationDate,
                command.minimumOverbiddingPrice
        );
        repository.save(auction);
        return auction.id;
    }
}

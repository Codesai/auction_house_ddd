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
        if (command.conquerPrice < command.initialBid) throw new InitialBidIsGreaterThanConquerPrice();
        if (command.minimumOverbiddingPrice < MINIMUM_MONEY_TO_OVERBID.amount) throw new MinimumOverbiddingPriceIsNotAllowed();
        var auction = new Auction(
                item(command.name, command.description),
                money(command.initialBid),
                money(command.conquerPrice),
                command.expirationDate,
                money(command.minimumOverbiddingPrice)
        );
        repository.save(auction);
        return auction.id;
    }
}

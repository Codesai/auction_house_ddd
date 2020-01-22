package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.actions.commands.CreateAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayIsTooFar;
import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.auction.exceptions.MinimumOverbiddingPriceIsNotAllowed;
import com.codesai.auction_house.business.model.generic.Money;

import static com.codesai.auction_house.business.model.auction.Item.item;
import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;

public class CreateAuctionAction {
    private static final Money MINIMUM_MONEY_TO_OVERBID = money(1);
    private AuctionRepository repository;

    public CreateAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public String execute(CreateAuctionCommand command) {
        if (command.conquerPrice.isLessThan(command.initialBid)) throw new InitialBidIsGreaterThanConquerPrice();
        if (command.minimumOverbiddingPrice.isLessThan(MINIMUM_MONEY_TO_OVERBID)) throw new MinimumOverbiddingPriceIsNotAllowed();
        if (command.expirationDate.isAfter(now().plusWeeks(2))) throw new ExpirationDayIsTooFar();
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

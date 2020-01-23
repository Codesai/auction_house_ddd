package helpers.builder;

import com.codesai.auction_house.business.actions.commands.CreateAuctionCommand;
import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;

public class CreateAuctionCommandBuilder {
    private LocalDate expirationDay = now().plusDays(14);
    private Money overbiddingPrice = money(1);
    private Money conquerPrice = money(5);
    private Money initialBid = money(1);
    private String name = "Any Name";
    private String description = "Any Description";

    private CreateAuctionCommandBuilder() {
    }

    public static CreateAuctionCommandBuilder aCreateAuctionCommand() {
        return new CreateAuctionCommandBuilder();
    }

    public CreateAuctionCommandBuilder withPassedExpirationDay() {
        this.expirationDay = now().minusDays(1);
        return this;
    }

    public CreateAuctionCommandBuilder withExpirationDay(LocalDate expirationDay) {
        this.expirationDay = expirationDay;
        return this;
    }

    public CreateAuctionCommandBuilder withMinimumOverbiddingPrice(Money overbiddingPrice) {
        this.overbiddingPrice = overbiddingPrice;
        return this;
    }

    public CreateAuctionCommandBuilder withInitialBidGreaterThanConquerPrice() {
        this.conquerPrice = money(15);
        this.initialBid = money(20);
        return this;
    }

    public CreateAuctionCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateAuctionCommandBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateAuctionCommandBuilder withInitialBid(Money initialBid) {
        this.initialBid = initialBid;
        return this;
    }

    public CreateAuctionCommandBuilder withConquerPrice(Money conquerPrice) {
        this.conquerPrice = conquerPrice;
        return this;
    }

    public CreateAuctionCommand build() {
        return new CreateAuctionCommand(
                name,
                description,
                initialBid.amount,
                conquerPrice.amount,
                expirationDay,
                overbiddingPrice.amount
        );
    }
}

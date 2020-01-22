package helpers.builder;

import com.codesai.auction_house.business.auction.Auction;
import com.codesai.auction_house.business.auction.Item;
import com.codesai.auction_house.business.generic.Money;

import java.time.LocalDate;

import static com.codesai.auction_house.business.auction.Item.item;
import static com.codesai.auction_house.business.generic.Money.money;
import static java.time.LocalDate.now;

public class AuctionBuilder {

    private Money conquerPrice = money(50);
    private Money initialBid = money(10.5);
    private Money minimumOverbiddingPrice = money(1);
    private LocalDate expirationDay = now().plusDays(14);
    private Item item = item("anyItem", "anyDescription");

    public static AuctionBuilder anAuction() {
        return new AuctionBuilder();
    }

    public AuctionBuilder setItem(Item item) {
        this.item = item;
        return this;
    }

    public AuctionBuilder setInitialBid(Money initialBid) {
        this.initialBid = initialBid;
        return this;
    }

    public AuctionBuilder setConquerPrice(Money conquerPrice) {
        this.conquerPrice = conquerPrice;
        return this;
    }

    public AuctionBuilder setExpirationDay(LocalDate expirationDay) {
        this.expirationDay = expirationDay;
        return this;
    }

    public AuctionBuilder setMinimumOverbiddingPrice(Money minimumOverbiddingPrice) {
        this.minimumOverbiddingPrice = minimumOverbiddingPrice;
        return this;
    }

    public Auction build() {
        return new Auction(
                item,
                initialBid,
                conquerPrice,
                expirationDay,
                minimumOverbiddingPrice
        );
    }
}

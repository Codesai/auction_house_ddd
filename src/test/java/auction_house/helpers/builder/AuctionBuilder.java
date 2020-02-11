package auction_house.helpers.builder;

import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.Item;
import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codesai.auction_house.business.model.auction.Item.item;
import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;

public class AuctionBuilder {

    private final Money conquerPrice = money(50);
    private Money startingPrice = money(10.5);
    private Money minimumOverbiddingPrice = money(1);
    private LocalDate expirationDay = now().plusDays(14);
    private final Item item = item("anyItem", "anyDescription");
    private List<Bid> bids = new ArrayList<>();
    private OwnerId ownerId = new OwnerId(UUID.randomUUID().toString());

    public static AuctionBuilder anAuction() {
        return new AuctionBuilder();
    }

    public AuctionBuilder withStartingPrice(Money startingPrice) {
        this.startingPrice = startingPrice;
        return this;
    }

    public AuctionBuilder withBids(List<Bid> bids) {
        this.bids = bids;
        return this;
    }

    public AuctionBuilder withExpirationDay(LocalDate expirationDay) {
        this.expirationDay = expirationDay;
        return this;
    }

    public Auction build() {
        var auction = new Auction(item, startingPrice, conquerPrice, expirationDay, ownerId);
        this.bids.forEach(auction::proposeBid);
        return auction;
    }

    public AuctionBuilder withOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}

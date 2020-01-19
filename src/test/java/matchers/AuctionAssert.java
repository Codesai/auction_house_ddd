package matchers;

import com.codesai.auction_house.business.auction.Auction;
import org.assertj.core.api.AbstractAssert;

public class AuctionAssert extends AbstractAssert<AuctionAssert, Auction> {
    public AuctionAssert(Auction auction) {
        super(auction, AuctionAssert.class);
    }

    public static AuctionAssert assertThat(Auction actual) {
        return new AuctionAssert(actual);
    }

    public AuctionAssert isEqualTo(Auction expected) {
        isNotNull();
        if (!actual.id.matches("[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}")) {
            failWithMessage("Expected auction id to be: " + expected.id + " but was " + expected.id);
        }
        if (!actual.item.equals(expected.item)) {
            failWithMessage(String.format("Expected auction item to be: %s but was %s", expected.item, expected.item));
        }
        if (!actual.initialBid.equals(expected.initialBid)) {
            failWithMessage(String.format("Expected initial bid to be: %s but was %s", expected.initialBid, expected.initialBid));
        }
        if (!actual.conquerPrice.equals(expected.conquerPrice)) {
            failWithMessage(String.format("Expected conquer price to be: %s but was %s", expected.conquerPrice, expected.conquerPrice));
        }
        if (!actual.expirationDate.equals(expected.expirationDate)) {
            failWithMessage(String.format("Expected expiration date to be: %s but was %s", expected.expirationDate, expected.expirationDate));
        }
        if (!actual.minimumOverbiddingPrice.equals(expected.minimumOverbiddingPrice)) {
            failWithMessage(String.format("Expected  minimum overbidding price to be: %s but was %s", expected.minimumOverbiddingPrice, expected.minimumOverbiddingPrice));
        }
        return this;
    }
}

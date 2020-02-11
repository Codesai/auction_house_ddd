package matchers;

import com.codesai.auction_house.business.model.auction.Auction;
import org.assertj.core.api.AbstractAssert;

public class AuctionAssert extends AbstractAssert<AuctionAssert, Auction> {
    public AuctionAssert(Auction auction) {
        super(auction, AuctionAssert.class);
    }

    public static AuctionAssert assertThatAuction(Auction actual) {
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
        if (!actual.startingPrice.equals(expected.startingPrice)) {
            failWithMessage(String.format("Expected initial bid to be: %s but was %s", expected.startingPrice, expected.startingPrice));
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
        if (!actual.ownerId.id.equals(expected.ownerId.id)) {
            failWithMessage(String.format("Expected owner was: %s but was %s", expected.ownerId.id, actual.ownerId.id));
        }
        return this;
    }
}

package matchers;

import com.codesai.auction_house.business.model.auction.Bid;
import org.assertj.core.api.AbstractAssert;

import java.util.UUID;

public class BidAssert extends AbstractAssert<BidAssert, Bid> {
    public BidAssert(Bid bid) {
        super(bid, BidAssert.class);
    }

    public static BidAssert assertThatBid(Bid expectedBid) {
        return new BidAssert(expectedBid);
    }

    public void isEqualTo(Bid expected) {
        isNotNull();

        try { UUID.fromString(actual.id); } catch (Exception e) {
            failWithMessage("Id: <%s> is not an UUID formatted string", actual.id);
        }

        if (!this.actual.money.equals(expected.money)) {
            failWithMessage("Expected money to be: <%s> and was: <%s>", actual.money, expected.money);
        }

        if (!this.actual.bidderId.equals(expected.bidderId)) {
            failWithMessage("Expected bidder id to be: <%s> and was: <%s>", actual.bidderId, expected.bidderId);
        }

    }

}

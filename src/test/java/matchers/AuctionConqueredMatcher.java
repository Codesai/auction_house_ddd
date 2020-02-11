package matchers;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.bidder.BidderId;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.time.LocalDate;
import java.util.Objects;

public class AuctionConqueredMatcher extends TypeSafeMatcher<Auction> {

    private final BidderId bidderId;

    public AuctionConqueredMatcher(BidderId bidderId) {
        this.bidderId = bidderId;
    }

    public static AuctionConqueredMatcher anAuctionConqueredBy(BidderId bidderId) {
        return new AuctionConqueredMatcher(bidderId);
    }

    @Override
    protected boolean matchesSafely(Auction auction) {
        return
            auction.expirationDate.equals(LocalDate.now().minusDays(1)) &&
            auction.topBid().map((bid) -> Objects.equals(bid.bidderId, bidderId)).orElse(false);
    }

    @Override
    public void describeTo(Description description) { description.appendText("with an auction won by: " + bidderId);}
}

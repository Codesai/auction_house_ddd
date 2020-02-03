package matchers;

import com.codesai.auction_house.business.model.auction.Auction;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.time.LocalDate;
import java.util.Objects;

public class AuctionConqueredMatcher extends TypeSafeMatcher<Auction> {

    private final String userId;

    public AuctionConqueredMatcher(String userId) {
        this.userId = userId;
    }

    public static AuctionConqueredMatcher anAuctionConqueredBy(String userId) {
        return new AuctionConqueredMatcher(userId);
    }

    @Override
    protected boolean matchesSafely(Auction auction) {
        return
            auction.expirationDate.equals(LocalDate.now().minusDays(1)) &&
            auction.topBid().map((bid) -> Objects.equals(bid.userId, userId)).orElse(false);
    }

    @Override
    public void describeTo(Description description) { description.appendText("with an auction won by: " + userId);}
}

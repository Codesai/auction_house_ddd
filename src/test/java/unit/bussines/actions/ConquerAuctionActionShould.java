package unit.bussines.actions;

import com.codesai.auction_house.business.actions.ConquerAuctionAction;
import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static helpers.builder.AuctionBuilder.anAuction;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static unit.bussines.actions.AuctionConqueredMatcher.*;

public class ConquerAuctionActionShould {


    private final AuctionRepository repository = mock(AuctionRepository.class);
    private final ConquerAuctionAction action = new ConquerAuctionAction(repository);
    private final String userId = "anyUser";

    @Test public void
    win_the_auction() {
        var anAuction = givenAnAuction();

        action.execute(new ConquerAuctionActionCommand(userId, anAuction.id));

        verify(repository).save(argThat(anAuctionIsConqueredBy(userId)));
    }

    private Auction givenAnAuction() {
        var anAuction = anAuction().build();
        when(repository.retrieveById(anAuction.id)).thenReturn(Optional.of(anAuction));
        return anAuction;
    }
}

class AuctionConqueredMatcher extends TypeSafeMatcher<Auction> {

    private final String userId;

    public AuctionConqueredMatcher(String userId) {
        this.userId = userId;
    }

    public static AuctionConqueredMatcher anAuctionIsConqueredBy(String userId) {
        return new AuctionConqueredMatcher(userId);
    }

    @Override
    protected boolean matchesSafely(Auction auction) {
        return
            auction.expirationDate.equals(LocalDate.now().minusDays(1)) &&
            auction.winner().map((bid) -> Objects.equals(bid.userId, userId)).orElse(false);
    }

    @Override
    public void describeTo(Description description) { description.appendText("with an auction won by: " + userId);}
}

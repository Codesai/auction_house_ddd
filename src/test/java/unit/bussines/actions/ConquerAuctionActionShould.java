package unit.bussines.actions;

import com.codesai.auction_house.business.actions.ConquerAuctionAction;
import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.CannotConquerAClosedAuctionException;
import org.junit.jupiter.api.Test;

import static helpers.builder.AuctionBuilder.anAuction;
import static matchers.AuctionConqueredMatcher.anAuctionConqueredBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class ConquerAuctionActionShould {

    private final AuctionRepository auctions = mock(AuctionRepository.class);
    private final ConquerAuctionAction conquerAuction = new ConquerAuctionAction(auctions);
    private final String userId = "anyUser";

    @Test public void
    win_the_auction() {
        var aLiveAuction = givenALiveAuction();

        conquerAuction.execute(new ConquerAuctionActionCommand(userId, aLiveAuction.id));

        verify(auctions).save(argThat(anAuctionConqueredBy(userId)));
    }
    
    @Test public void
    cannot_conquer_a_closed_auction() throws Exception {
        var aClosedAuction = aClosedAuction();

        assertThatThrownBy(() -> conquerAuction.execute(new ConquerAuctionActionCommand(userId, aClosedAuction.id))).isInstanceOf(CannotConquerAClosedAuctionException.class);
    }

    private Auction aClosedAuction() {
        var auction = givenALiveAuction();
        conquerAuction.execute(new ConquerAuctionActionCommand(userId, auction.id));
        return auction;
    }

    private Auction givenALiveAuction() {
        var anAuction = anAuction().build();
        when(auctions.retrieveById(anAuction.id)).thenReturn(anAuction);
        return anAuction;
    }
}


package auction_house.unit.bussines.actions;

import com.codesai.auction_house.business.actions.DeclareAuctionWinnerAction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.EventProducer;
import com.codesai.auction_house.business.model.auction.events.DeclareWinnerEvent;
import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.generic.Calendar;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;
import static org.mockito.Mockito.*;

public class DeclareAuctionWinnerActionShould {

    final LocalDate TODAY = now();
    final AuctionRepository auctionRepository = mock(AuctionRepository.class);
    final Calendar calendar = mock(Calendar.class);
    final EventProducer eventProducer = mock(EventProducer.class);
    final String ANY_BIDDER_ID = "anyUser";

    @Test
    public void
    declare_the_top_bid_as_the_winner() {
        var expectedBidder = new BidderId(ANY_BIDDER_ID);
        var auction = anAuction()
                .withExpirationDay(TODAY)
                .withStartingPrice(money(1))
                .withBids(List.of(new Bid(money(2), expectedBidder)))
                .build();

        when(calendar.yesterday()).thenReturn(TODAY);
        when(auctionRepository.retrieveAll()).thenReturn(List.of(auction));

        new DeclareAuctionWinnerAction(auctionRepository, calendar, eventProducer).execute();

        verify(eventProducer).produce(new DeclareWinnerEvent(
                expectedBidder.id,
                auction.id,
                money(2)
        ));
    }
}

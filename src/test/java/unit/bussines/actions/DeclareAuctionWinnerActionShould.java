package unit.bussines.actions;

import com.codesai.auction_house.business.actions.DeclareAuctionWinnerAction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.EventProducer;
import com.codesai.auction_house.business.model.auction.events.DeclareWinnerEvent;
import com.codesai.auction_house.business.model.generic.Calendar;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static helpers.builder.AuctionBuilder.anAuction;
import static org.mockito.Mockito.*;

public class DeclareAuctionWinnerActionShould {

    @Test
    public void
    declare_the_top_bid_as_the_winner() {
        var today = LocalDate.now();
        var auction = anAuction()
                .withExpirationDay(today)
                .withStartingPrice(money(1))
                .withBid(new Bid(money(2), "anyUser"))
                .build();

        var auctionRepository = mock(AuctionRepository.class);
        var calendar = mock(Calendar.class);
        var eventProducer = mock(EventProducer.class);
        when(calendar.today()).thenReturn(today.plusDays(1));
        when(auctionRepository.retrieveAll()).thenReturn(List.of(auction));

        new DeclareAuctionWinnerAction(auctionRepository, calendar, eventProducer)
                .execute();

        verify(eventProducer).produce(new DeclareWinnerEvent(
                "anyUser",
                auction.id,
                money(2)
        ));
    }
}

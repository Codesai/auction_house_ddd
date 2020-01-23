package unit.bussines.actions;

import com.codesai.auction_house.business.actions.BidAuctionAction;
import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static helpers.builder.AuctionBuilder.anAuction;
import static matchers.BidAssert.assertThatBid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BidAuctionActionShould {

    @Test
    public void
    bid_an_auction_when_is_greater_than_the_current_bid() {
        var auctionId = "anAuctionId";
        var expectedAmount = 50.0;
        var repository = mock(AuctionRepository.class);
        when(repository.retrieveById(auctionId)).thenReturn(Optional.of(anAuction().build()));

        new BidAuctionAction(repository).execute(new BidAuctionCommand(auctionId, expectedAmount));

        var captor = ArgumentCaptor.forClass(Auction.class);
        verify(repository, times(1)).save(captor.capture());
        assertThat(captor.getValue().bids).hasSize(1);
        assertThatBid(captor.getValue().bids.get(0)).isEqualTo(new Bid(money(expectedAmount)));
    }
}

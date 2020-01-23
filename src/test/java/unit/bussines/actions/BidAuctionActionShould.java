package unit.bussines.actions;

import com.codesai.auction_house.business.actions.BidAuctionAction;
import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.CurrentBidIsGreater;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static helpers.builder.AuctionBuilder.anAuction;
import static matchers.BidAssert.assertThatBid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BidAuctionActionShould {

    AuctionRepository repository = mock(AuctionRepository.class);
    BidAuctionAction action = new BidAuctionAction(this.repository);
    ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);

    @Test
    public void
    bid_an_auction_when_is_greater_than_the_current_bid() {
        var auctionId = "anAuctionId";
        var expectedAmount = 50.0;
        when(this.repository.retrieveById(auctionId)).thenReturn(Optional.of(anAuction().withInitialBid(20).build()));

        action.execute(new BidAuctionCommand(auctionId, expectedAmount));

        verify(this.repository, times(1)).save(this.captor.capture());
        assertThat(this.captor.getValue().bids).hasSize(2);
        assertThatBid(this.captor.getValue().bids.get(0)).isEqualTo(new Bid(money(expectedAmount)));
    }
    @Test
    public void
    not_allow_to_bid_an_auction_when_is_lesser_than_the_current_bid() {
        var auctionId = "anAuctionId";
        var expectedAmount = 10;
        when(this.repository.retrieveById(auctionId)).thenReturn(Optional.of(anAuction().withInitialBid(50).build()));

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(auctionId, expectedAmount)))
                .isInstanceOf(CurrentBidIsGreater.class);
    }
}

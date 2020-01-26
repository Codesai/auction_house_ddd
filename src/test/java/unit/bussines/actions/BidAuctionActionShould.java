package unit.bussines.actions;

import com.codesai.auction_house.business.actions.BidAuctionAction;
import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.BidAmountCannotBeTheSameAsTheCurrentOne;
import com.codesai.auction_house.business.model.auction.exceptions.FirstBidShouldBeGreaterThanStartingPrice;
import com.codesai.auction_house.business.model.auction.exceptions.TopBidIsGreater;
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

    String ANY_AUCTION_ID = "anAuctionId";
    AuctionRepository repository = mock(AuctionRepository.class);
    BidAuctionAction action = new BidAuctionAction(this.repository);
    ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);

    @Test
    public void
    bid_an_auction_when_is_greater_than_the_starting_price_bid() {
        var expectedAmount = 50.0;
        when(this.repository.retrieveById(ANY_AUCTION_ID)).thenReturn(Optional.of(anAuction().withStartingPrice(money(20)).build()));

        action.execute(new BidAuctionCommand(ANY_AUCTION_ID, expectedAmount));

        verify(this.repository, times(1)).save(this.captor.capture());
        assertThat(this.captor.getValue().bids).hasSize(1);
        assertThatBid(this.captor.getValue().bids.get(0)).isEqualTo(new Bid(money(expectedAmount)));
    }

    @Test
    public void
    bid_an_auction_when_is_greater_than_the_top_bid() {
        var auction = Optional.of(anAuction().withStartingPrice(money(20)).withBid(new Bid(money(25))).build());
        when(this.repository.retrieveById(ANY_AUCTION_ID)).thenReturn(auction);

        action.execute(new BidAuctionCommand(ANY_AUCTION_ID, 30));

        verify(repository, times(1)).save(captor.capture());
        assertThat(captor.getValue().bids).hasSize(2);
        assertThatBid(captor.getValue().bids.get(0)).isEqualTo(new Bid(money(30)));
    }

    @Test
    public void
    not_bid_an_auction_when_with_no_bids_the_starting_price_is_greater_the_new_bid() {
        var auction = Optional.of(anAuction().withStartingPrice(money(30)).build());
        when(this.repository.retrieveById(ANY_AUCTION_ID)).thenReturn(auction);

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(ANY_AUCTION_ID, 29)))
            .isInstanceOf(FirstBidShouldBeGreaterThanStartingPrice.class);
        verify(repository, times(0)).save(any());
    }

    @Test
    public void
    not_allow_to_bid_an_auction_when_the_top_bid_amount_is_the_same() {
        var expectedAmount = 50;
        when(this.repository.retrieveById(ANY_AUCTION_ID))
                .thenReturn(Optional.of(anAuction().withBid(new Bid(money(expectedAmount))).build()));

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(ANY_AUCTION_ID, expectedAmount)))
                .isInstanceOf(BidAmountCannotBeTheSameAsTheCurrentOne.class);
    }

    @Test
    public void
    not_allow_to_bid_an_auction_when_is_lesser_than_the_current_bid() {
        var amount = 10;
        var auctionBuilder = anAuction().withStartingPrice(money(amount)).withBid(new Bid(money(15)));
        when(this.repository.retrieveById(ANY_AUCTION_ID))
                .thenReturn(Optional.of(auctionBuilder.build()));

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(ANY_AUCTION_ID, 5)))
                .isInstanceOf(TopBidIsGreater.class);
    }
}

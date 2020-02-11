package auction_house.unit.bussines.actions;

import com.codesai.auction_house.business.actions.BidAuctionAction;
import com.codesai.auction_house.business.actions.commands.BidAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.BidAmountCannotBeTheSameAsTheCurrentOne;
import com.codesai.auction_house.business.model.auction.exceptions.FirstBidShouldBeGreaterThanStartingPrice;
import com.codesai.auction_house.business.model.auction.exceptions.TopBidIsGreater;
import com.codesai.auction_house.business.model.bidder.Bidder;
import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.bidder.BidderRepository;
import com.codesai.auction_house.business.model.generic.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static auction_house.matchers.BidAssert.assertThatBid;
import static com.codesai.auction_house.business.model.generic.Money.money;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BidAuctionActionShould {

    final String ANY_BIDDER_ID = "AnyBidderId";
    final AuctionRepository auctionRepository = mock(AuctionRepository.class);
    final BidderRepository bidderRepository = mock(BidderRepository.class);
    final BidAuctionAction action = new BidAuctionAction(auctionRepository, bidderRepository);
    final ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);

    @BeforeEach
    public void setUp() {
        when(bidderRepository.retrieveById(any())).thenReturn(new Bidder(new BidderId(ANY_BIDDER_ID)));
    }

    @Test
    public void
    bid_an_auction_when_is_greater_than_the_starting_price_bid() {
        var expectedAmount = 50.0;
        var auction = givenAnAuctionWithNoBidsAndStartingPriceAt(money(20));

        action.execute(new BidAuctionCommand(auction.id, expectedAmount, ANY_BIDDER_ID));

        verify(auctionRepository, times(1)).save(captor.capture());
        verify(bidderRepository, times(1)).save();
        assertThat(captor.getValue().bids).hasSize(1);
        assertThatBid(captor.getValue().bids.get(0)).isEqualTo(new Bid(money(expectedAmount), new BidderId(ANY_BIDDER_ID)));
    }

    @Test
    public void
    bid_an_auction_when_is_greater_than_the_top_bid() {
        var expectedAmount = 30;
        var auction = givenAnAuctionWithStartingPriceAndBids(money(20), List.of(new Bid(money(25), new BidderId(ANY_BIDDER_ID))));

        action.execute(new BidAuctionCommand(auction.id, expectedAmount, ANY_BIDDER_ID));

        verify(auctionRepository, times(1)).save(captor.capture());
        verify(bidderRepository, times(1)).save();
        assertThat(captor.getValue().bids).hasSize(2);
        assertThatBid(captor.getValue().bids.get(0)).isEqualTo(new Bid(money(expectedAmount), new BidderId(ANY_BIDDER_ID)));
    }

    @Test
    public void
    not_bid_an_auction_when_with_no_bids_the_starting_price_is_greater_the_new_bid() {
        var auction = givenAnAuctionWithNoBidsAndStartingPriceAt(money(30));

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(auction.id, 29, ANY_BIDDER_ID)))
            .isInstanceOf(FirstBidShouldBeGreaterThanStartingPrice.class);
        verify(auctionRepository, times(0)).save(any());
        verify(bidderRepository, times(0)).save();
    }

    @Test
    public void
    not_allow_to_bid_an_auction_when_the_top_bid_amount_is_the_same() {
        var expectedAmount = 50;
        var auction = givenAnAuctionWithStartingPriceAndBids(money(10), List.of(new Bid(money(expectedAmount), new BidderId(ANY_BIDDER_ID))));

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(auction.id, expectedAmount, ANY_BIDDER_ID)))
                .isInstanceOf(BidAmountCannotBeTheSameAsTheCurrentOne.class);
        verify(bidderRepository, times(0)).save();
        verify(auctionRepository, times(0)).save(any());
    }

    @Test
    public void
    not_allow_to_bid_an_auction_when_is_lesser_than_the_current_bid() {
        var auction = givenAnAuctionWithStartingPriceAndBids(money(10), List.of(new Bid(money(15), new BidderId(ANY_BIDDER_ID))));

        assertThatThrownBy(() -> action.execute(new BidAuctionCommand(auction.id, 5, ANY_BIDDER_ID)))
                .isInstanceOf(TopBidIsGreater.class);
    }

    private Auction givenAnAuctionWithNoBidsAndStartingPriceAt(Money startingPrice) {
        var auction = anAuction().withStartingPrice(startingPrice).build();
        when(auctionRepository.retrieveById(auction.id)).thenReturn(auction);
        return auction;
    }

    private Auction givenAnAuctionWithStartingPriceAndBids(Money startingPrice, List<Bid> bids) {
        var auction = anAuction().withStartingPrice(startingPrice).withBids(bids).build();
        when(auctionRepository.retrieveById(auction.id)).thenReturn(auction);
        return auction;
    }
}

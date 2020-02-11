package unit.bussines.actions;

import com.codesai.auction_house.business.actions.commands.RetrieveAuctionBidsAction;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionBidsActionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.bidder.BidderId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static helpers.builder.AuctionBuilder.anAuction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrieveAuctionBidsActionShould {

    final AuctionRepository repository = mock(AuctionRepository.class);
    final RetrieveAuctionBidsAction action = new RetrieveAuctionBidsAction(this.repository);

    @Test
    public void
    return_all_the_bids_from_an_auction() {
        var expectedBids = List.of(new Bid(money(10), new BidderId("AnyBidderId")), new Bid(money(15), new BidderId("AnyBidderId")), new Bid(money(20), new BidderId("AnyBidderId")));
        var auction = anAuction().withStartingPrice(money(5))
                .withBids(expectedBids)
                .build();
        when(this.repository.retrieveById(auction.id)).thenReturn(auction);

        var result = action.execute(new RetrieveAuctionBidsActionCommand(auction.id));

        assertThat(result).hasSize(3);
        assertThat(result).containsAll(expectedBids);
    }

}

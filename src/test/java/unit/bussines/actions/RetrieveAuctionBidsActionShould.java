package unit.bussines.actions;

import com.codesai.auction_house.business.actions.commands.RetrieveAuctionBidsAction;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionBidsActionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.Bid;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static helpers.builder.AuctionBuilder.anAuction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrieveAuctionBidsActionShould {

    AuctionRepository repository = mock(AuctionRepository.class);
    RetrieveAuctionBidsAction action = new RetrieveAuctionBidsAction(this.repository);

    @Test
    public void
    return_all_the_bids_from_an_auction() {
        var firstBid = new Bid(money(5));
        var secondBid = new Bid(money(10));
        var thirdBid = new Bid(money(15));
        var fourthBid = new Bid(money(20));
        var auction = anAuction().withStartingPrice(firstBid.money)
                .withBid(secondBid)
                .withBid(thirdBid)
                .withBid(fourthBid)
                .build();
        when(this.repository.retrieveById(auction.id)).thenReturn(Optional.of(auction));

        var result = action.execute(new RetrieveAuctionBidsActionCommand(auction.id));

        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrder(firstBid, secondBid, thirdBid, fourthBid);
    }

}

package auction_house.unit.bussines.actions;

import com.codesai.auction_house.business.actions.RetrieveAuctionAction;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import org.junit.jupiter.api.Test;

import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static auction_house.matchers.AuctionAssert.assertThatAuction;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RetrieveAuctionActionShould {

    final AuctionRepository repository = mock(AuctionRepository.class);
    final RetrieveAuctionAction action = new RetrieveAuctionAction(this.repository);

    @Test
    public void
    retrieve_an_auction_by_its_id_when_exists() {
        var expectedAuction = anAuction().build();
        when(this.repository.retrieveById(expectedAuction.id)).thenReturn(expectedAuction);

        Auction actualAuction = action.execute(new RetrieveAuctionCommand(expectedAuction.id));

        assertThatAuction(actualAuction).isEqualTo(expectedAuction);
    }

}
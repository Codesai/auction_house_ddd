package unit.bussines.actions;

import com.codesai.auction_house.business.actions.RetrieveAuctionAction;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static helpers.builder.AuctionBuilder.anAuction;
import static matchers.AuctionAssert.assertThatAuction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RetrieveAuctionActionShould {

    @Test
    public void
    retrieve_an_auction_by_its_id_when_exists() {
        var repository = mock(AuctionRepository.class);
        var expectedAuction = anAuction().build();
        when(repository.retrieveById(expectedAuction.id)).thenReturn(Optional.of(expectedAuction));

        var actualAuction = new RetrieveAuctionAction(repository).execute(new RetrieveAuctionCommand(expectedAuction.id));

        assertThat(actualAuction.isPresent()).isTrue();
        assertThatAuction(actualAuction.get()).isEqualTo(expectedAuction);
    }

}
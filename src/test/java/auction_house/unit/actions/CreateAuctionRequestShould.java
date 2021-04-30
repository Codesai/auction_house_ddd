package auction_house.unit.actions;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.CreateAuctionRequest;
import com.codesai.auction_house.business.model.auction.repository.AuctionRepository;
import com.codesai.auction_house.business.model.generic.Money;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateAuctionRequestShould {

    public static final String ANY_ITEM_NAME = "An Item" + UUID.randomUUID();
    public static final String ANY_DESCRIPTION = "AnyDescription" + UUID.randomUUID();
    public static final double ANY_INITIAL_BID_AMOUNT = 50.0;
    public static final double ANY_CONQUER_PRICE_AMOUNT = 100.0;
    public static final LocalDate ANY_EXPIRATION_DAY = now();
    public static final String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID();
    private final AuctionRepository repository = mock(AuctionRepository.class);
    private final ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);

    @Test
    public void
    creates_an_auction() {
        var request = new CreateAuctionRequest(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                ANY_INITIAL_BID_AMOUNT,
                ANY_CONQUER_PRICE_AMOUNT,
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID
        );

        var auctionId = new CreateAuctionCommand(repository).execute(request);

        verify(repository).save(captor.capture());
        assertThat(captor.getValue().id).isEqualTo(auctionId);
        assertThat(captor.getValue().name).isEqualTo(ANY_ITEM_NAME);
        assertThat(captor.getValue().description).isEqualTo(ANY_DESCRIPTION);
        assertThat(captor.getValue().initialBid).isEqualTo(new Money(ANY_INITIAL_BID_AMOUNT));
        assertThat(captor.getValue().conquerPrice).isEqualTo(new Money(ANY_CONQUER_PRICE_AMOUNT));
        assertThat(captor.getValue().expirationDay).isEqualTo(ANY_EXPIRATION_DAY);
        assertThat(captor.getValue().ownerId).isEqualTo(ANY_OWNER_ID);
    }
}

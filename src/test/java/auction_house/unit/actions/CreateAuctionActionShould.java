package auction_house.unit.actions;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.business.model.Auction;
import com.codesai.auction_house.business.model.AuctionRepository;
import com.codesai.auction_house.business.model.exceptions.ExpirationDayIsBeforeThanTodayException;
import com.codesai.auction_house.business.model.exceptions.InitialBidCannotBeGreaterThanConquerPriceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.UUID;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CreateAuctionActionShould {

    public static final String ANY_ITEM_NAME = "An Item" + UUID.randomUUID();
    public static final String ANY_DESCRIPTION = "AnyDescription" + UUID.randomUUID();
    public static final double ANY_INITIAL_BID_AMOUNT = 50.0;
    public static final double ANY_CONQUER_PRICE_AMOUNT = 100.0;
    public static final LocalDate ANY_EXPIRATION_DAY = now();
    public static final String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID();

    private final AuctionRepository repository = mock(AuctionRepository.class);
    private final CreateAuctionAction action = new CreateAuctionAction(repository);
    private final ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);

    @Test
    public void
    creates_auction() {
        var command = new CreateAuctionCommand(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                ANY_INITIAL_BID_AMOUNT,
                ANY_CONQUER_PRICE_AMOUNT,
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID
        );

        action.execute(command);

        verify(repository, times(1)).save(captor.capture());
        Assertions.assertDoesNotThrow(() -> UUID.fromString(captor.getValue().id));
        assertThat(captor.getValue().name).isEqualTo(ANY_ITEM_NAME);
        assertThat(captor.getValue().description).isEqualTo(ANY_DESCRIPTION);
        assertThat(captor.getValue().initialBidAmount).isEqualTo(money(ANY_INITIAL_BID_AMOUNT));
        assertThat(captor.getValue().conquerPriceAmount).isEqualTo(money(ANY_CONQUER_PRICE_AMOUNT));
        assertThat(captor.getValue().expirationDay).isEqualTo(ANY_EXPIRATION_DAY);
        assertThat(captor.getValue().ownerId).isEqualTo(ANY_OWNER_ID);
    }

    @Test
    public void
    not_create_an_auction_when_the_expiration_day_is_before_than_the_current_day() {
        var command = new CreateAuctionCommand(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                ANY_INITIAL_BID_AMOUNT,
                ANY_CONQUER_PRICE_AMOUNT,
                LocalDate.now().minusDays(1),
                ANY_OWNER_ID
        );

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(ExpirationDayIsBeforeThanTodayException.class);

        verify(repository, times(0)).save(any());
    }

    @Test
    public void
    not_create_an_auction_when_the_initial_bid_is_greater_than_the_conquer_price() {
        var command = new CreateAuctionCommand(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                ANY_CONQUER_PRICE_AMOUNT + 1,
                ANY_CONQUER_PRICE_AMOUNT,
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID
        );

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(InitialBidCannotBeGreaterThanConquerPriceException.class);

        verify(repository, times(0)).save(any());
    }
}

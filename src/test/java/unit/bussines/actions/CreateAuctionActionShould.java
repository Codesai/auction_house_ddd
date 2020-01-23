package unit.bussines.actions;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayAlreadyPassed;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayIsTooFar;
import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.auction.exceptions.MinimumOverbiddingPriceIsNotAllowed;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static helpers.builder.AuctionBuilder.anAuction;
import static helpers.builder.CreateAuctionCommandBuilder.anCreateAuctionCommand;
import static java.time.LocalDate.now;
import static matchers.AuctionAssert.assertThatAuction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CreateAuctionActionShould {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);
    ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);
    CreateAuctionAction action = new CreateAuctionAction(auctionRepository);

    @Test public void
    create_an_auction() {
        var expectedAuction = anAuction().build();
        var createAuctionCommand = anCreateAuctionCommand()
                .withName(expectedAuction.item.name)
                .withDescription(expectedAuction.item.description)
                .withInitialBid(expectedAuction.initialBid)
                .withConquerPrice(expectedAuction.conquerPrice)
                .withExpirationDay(expectedAuction.expirationDate)
                .withMinimumOverbiddingPrice(expectedAuction.minimumOverbiddingPrice)
                .build();

        var actualId = action.execute(createAuctionCommand);

        verify(auctionRepository, times(1)).save(captor.capture());
        assertThat(actualId).isEqualTo(captor.getValue().id);
        assertThatAuction(captor.getValue()).isEqualTo(expectedAuction);
    }

    @Test public void
    not_create_an_auction_when_conquer_price_is_greater_than_initial_bid() {
        var command = anCreateAuctionCommand()
                        .withInitialBidGreaterThanConquerPrice()
                        .build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(InitialBidIsGreaterThanConquerPrice.class);
    }

    @Test public void
    not_create_an_auction_when_the_minimum_overbidding_price_is_less_than_an_euro() {
        var command = anCreateAuctionCommand()
                        .withMinimumOverbiddingPrice(money(0.5))
                        .build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(MinimumOverbiddingPriceIsNotAllowed.class);
    }

    @Test public void
    not_create_an_auction_when_the_expiration_date_is_more_than_2_weeks_from_today() {
        var command = anCreateAuctionCommand()
                        .withExpirationDay(now().plusDays(15))
                        .build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(ExpirationDayIsTooFar.class);
    }

    @Test public void
    not_create_an_auction_when_the_expiration_date_is_before_than_today() {
        var command = anCreateAuctionCommand()
                        .withPassedExpirationDay().build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(ExpirationDayAlreadyPassed.class);
    }

}

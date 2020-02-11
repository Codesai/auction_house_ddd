package unit.bussines.actions;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.model.OwnerId;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayAlreadyPassed;
import com.codesai.auction_house.business.model.auction.exceptions.ExpirationDayIsTooFar;
import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.owner.Owner;
import com.codesai.auction_house.business.model.owner.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static helpers.builder.AuctionBuilder.anAuction;
import static helpers.builder.CreateAuctionCommandBuilder.aCreateAuctionCommand;
import static java.time.LocalDate.now;
import static matchers.AuctionAssert.assertThatAuction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CreateAuctionActionShould {

    public static final OwnerId ANY_OWNER_ID = new OwnerId("ANY_OWNER_ID");
    AuctionRepository auctionRepository = mock(AuctionRepository.class);
    OwnerRepository ownerRepository = mock(OwnerRepository.class);
    ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);
    CreateAuctionAction action = new CreateAuctionAction(auctionRepository, ownerRepository);

    @BeforeEach
    public void setUp() {
        when(ownerRepository.retrieveById(any())).thenReturn(new Owner(ANY_OWNER_ID));
    }

    @Test public void
    create_an_auction() {
        var expectedAuction = anAuction().withOwnerId(ANY_OWNER_ID).build();
        var createAuctionCommand = aCreateAuctionCommand()
                .withName(expectedAuction.item.name)
                .withDescription(expectedAuction.item.description)
                .withInitialBid(expectedAuction.startingPrice)
                .withConquerPrice(expectedAuction.conquerPrice)
                .withExpirationDay(expectedAuction.expirationDate)
                .withOwnerId(expectedAuction.ownerId.id)
                .build();

        var actualId = action.execute(createAuctionCommand);

        verify(auctionRepository, times(1)).save(captor.capture());
        verify(ownerRepository, times(1)).save(any());
        assertThat(actualId).isEqualTo(captor.getValue().id);
        assertThatAuction(captor.getValue()).isEqualTo(expectedAuction);
    }

    @Test public void
    not_create_an_auction_when_conquer_price_is_greater_than_initial_bid() {
        var command = aCreateAuctionCommand()
                        .withInitialBidGreaterThanConquerPrice()
                        .build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(InitialBidIsGreaterThanConquerPrice.class);
        verify(auctionRepository, times(0)).save(any());
        verify(ownerRepository, times(0)).save(any());
    }
    @Test public void
    not_create_an_auction_when_the_expiration_date_is_more_than_2_weeks_from_today() {
        var command = aCreateAuctionCommand()
                        .withExpirationDay(now().plusDays(15))
                        .build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(ExpirationDayIsTooFar.class);
        verify(auctionRepository, times(0)).save(any());
        verify(ownerRepository, times(0)).save(any());
    }

    @Test public void
    not_create_an_auction_when_the_expiration_date_is_before_than_today() {
        var command = aCreateAuctionCommand()
                        .withPassedExpirationDay()
                        .build();

        assertThatThrownBy(() -> action.execute(command))
                .isInstanceOf(ExpirationDayAlreadyPassed.class);
        verify(auctionRepository, times(0)).save(any());
        verify(ownerRepository, times(0)).save(any());
    }

}

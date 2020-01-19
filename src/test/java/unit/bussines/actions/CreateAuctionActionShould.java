package unit.bussines.actions;

import com.codesai.auction_house.business.Auction;
import com.codesai.auction_house.business.AuctionRepository;
import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.business.auction.Item;
import com.codesai.auction_house.business.generic.Money;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateAuctionActionShould {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);

    @Test public void
    create_an_auction() {
        final var name = "anyItem";
        final var description = "anyDescription";
        final var initialBid = 10.5;
        final var conquerPrice = 50;
        final var expirationDate = LocalDate.now().plusDays(15);
        final var minimumOverbiddingPrice = 1;
        var createAuctionCommand = new CreateAuctionCommand(
                name,
                description,
                initialBid,
                conquerPrice,
                expirationDate,
                minimumOverbiddingPrice
        );
        var expectedAuction = new Auction(
                new Item(name, description),
                Money.money(initialBid), Money.money(conquerPrice),
                expirationDate,
                Money.money(minimumOverbiddingPrice)
        );

        new CreateAuctionAction(auctionRepository).execute(createAuctionCommand);
        ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);
        verify(auctionRepository).save(captor.capture());
        final var actualAuction = captor.getValue();
        assertThat(actualAuction.id).matches("[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}");
        assertThat(actualAuction.item).isEqualTo(expectedAuction.item);
        assertThat(actualAuction.initialBid).isEqualTo(expectedAuction.initialBid);
        assertThat(actualAuction.conquerPrice).isEqualTo(expectedAuction.conquerPrice);
        assertThat(actualAuction.expirationDate).isEqualTo(expectedAuction.expirationDate);
        assertThat(actualAuction.minimumOverbiddingPrice).isEqualTo(expectedAuction.minimumOverbiddingPrice);
    }


}

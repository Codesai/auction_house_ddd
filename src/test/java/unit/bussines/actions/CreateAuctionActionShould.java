package unit.bussines.actions;

import com.codesai.auction_house.business.AuctionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateAuctionActionShould {

    AuctionRepository auctionRepository = mock(AuctionRepository.class);

    @Test public void
    create_an_auction() {
        var createAuctionCommand = new CreateAuctionCommand(
                new Item("anyItem", "anyDescription"),
                10.5,
                50,
                LocalDateTime.now().plusDays(15),
                1
        );

        var createAuctionAction = new CreateAuctionAction();
        createAuctionAction.execute(createAuctionCommand);

        verify(auctionRepository).save(argThat(auction -> false));
    }

    private class CreateAuctionCommand {
        private final Item item;
        private final double initialBid;
        private final double conquerPrice;
        private final LocalDateTime endDate;
        private final int minimumOverbiddingPrice;

        public CreateAuctionCommand(Item item, double initialBid, double conquerPrice, LocalDateTime endDate, int minimumOverbiddingPrice) {
            this.item = item;
            this.initialBid = initialBid;
            this.conquerPrice = conquerPrice;
            this.endDate = endDate;
            this.minimumOverbiddingPrice = minimumOverbiddingPrice;
        }
    }


    private class Item {
        public Item(String item, String description) {
        }
    }

    private class CreateAuctionAction {
        public UUID execute(CreateAuctionCommand createAuctionCommand) {
            return UUID.randomUUID();
        }
    }
}

package auction_house.acceptance;

import com.codesai.auction_house.business.actions.commands.ConquerAuctionActionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.bidder.BidderId;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static auction_house.matchers.AuctionConqueredMatcher.anAuctionConqueredBy;
import static com.codesai.auction_house.infrastructure.ActionFactory.auctionRepository;
import static com.codesai.auction_house.infrastructure.ActionFactory.conquerAuctionAction;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ConquerAuctionAPIShould extends ApiTest {

    @Test
    public void
    conquer_an_existing_auction() throws JSONException {
        var givenAuctionId = givingAnExistingAuction().id;

        given().
        when().
            body(new JSONObject()
                .put("conqueror_id", "userThatConquerAuction").toString()).
            post("auction/{id}/conquer", givenAuctionId).
        then().
            assertThat().
            statusCode(200).
            body(equalTo("OK"));

        assertThat(
                auctionRepository().retrieveById(givenAuctionId),
                anAuctionConqueredBy(new BidderId("userThatConquerAuction"))
        );
    }

    @Test public void
    cannot_conquer_an_already_closed_auction() throws Exception {
        var aClosedAuction = aClosedAuction();

        given().
        when().
            body(new JSONObject().put("conqueror_id", "userThatConquerAuction").toString()).
            post("auction/{auction_id}/conquer", aClosedAuction.id).
        then().
            assertThat().
            statusCode(422).
            body(
                    "name",equalTo("CannotConquerAClosedAuctionException"),
                    "description", equalTo("Cannot conquer a closed auction")
            );
    }

    private Auction givingAnExistingAuction() {
        var expectedAuction = anAuction().build();
        auctionRepository.save(expectedAuction);
        return expectedAuction;
    }

    private Auction aClosedAuction() {
        var auction = givenALiveAuction();
        conquerAuctionAction().execute(new ConquerAuctionActionCommand("", auction.id));
        return auction;
    }

    private Auction givenALiveAuction() {
        var anAuction = anAuction().build();
        auctionRepository.save(anAuction);
        return anAuction;
    }
}

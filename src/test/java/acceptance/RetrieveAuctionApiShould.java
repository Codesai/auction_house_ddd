package acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import org.junit.jupiter.api.Test;

import static helpers.builder.AuctionBuilder.anAuction;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class RetrieveAuctionApiShould extends ApiTest {

    @Test
    public void
    get_an_auction_by_its_id() throws Exception {
        var expectedAuction = givingAnExistingAuction();
        var auctionJson = JSONBuilder.createJsonFrom(expectedAuction);

        given().
                when().
                    get("auction/{id}", expectedAuction.id).
                then().
                    assertThat().
                    statusCode(200).
                    header("Content-type", "application/json").
                    body(equalTo(auctionJson.toString()));
    }

    @Test
    public void
    get_a_404_when_the_auction_does_not_exists() {
        given().
                when().
                    get("auction/{id}", "non-existing-auction-id").
                then().
                    assertThat().
                    statusCode(404).
                    body(equalTo("An auction with that id does not exists."));
    }

    private Auction givingAnExistingAuction() {
        var expectedAuction = anAuction().build();
        this.auctionRepository.save(expectedAuction);
        return expectedAuction;
    }

}
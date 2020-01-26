package acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static com.codesai.auction_house.infrastructure.ActionFactory.auctionRepository;
import static com.codesai.auction_house.infrastructure.Routing.PORT;
import static com.codesai.auction_house.infrastructure.Routing.Routes;
import static helpers.builder.AuctionBuilder.anAuction;
import static io.restassured.RestAssured.given;
import static matchers.BidAssert.assertThatBid;
import static matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;

public class AuctionHouseApiShould {
    InMemoryAuctionRepository auctionRepository = auctionRepository();

    @BeforeEach
    public void setUp() {
        auctionRepository.clean();
    }

    @BeforeAll
    static void startServer() {
        Routes();
        RestAssured.baseURI = String.format("http://localhost:%s/api/", PORT);
        awaitInitialization();
    }

    @AfterAll
    static void stopServer() {
        awaitStop();
    }

    @Test
    public void
    return_an_okay_when_is_running_on_the_status_route() {
        given().
                when().
                    get("status").
                then().
                    assertThat().
                    statusCode(200).
                    body(equalTo("OK"));
    }

    @Test
    public void
    create_a_new_auction() throws Exception {
        given().
                when().
                    body(createJsonFrom(anAuction().build()).toString()).
                    post("auction").
                then().
                    assertThat().
                    statusCode(201).
                    header("Location", allOf(
                            startsWith(RestAssured.baseURI + "auction/"),
                            urlEndsWithValidUUID()
                    ));
    }

    @Test
    public void
    not_create_an_auction_when_the_input_is_incorrect() throws Exception {

        given().
                when().
                    body(createJsonFrom(anAuction().build())
                        .put("initial_bid", "an invalid value")
                        .toString()).
                    post("auction").
                then().
                    assertThat().
                    statusCode(422).
                    body(equalTo("The auction body is not well formed."));
    }

    @Test
    public void
    get_an_auction_by_its_id() throws Exception {
        var expectedAuction = givingAnExistingAuction();
        var auctionJson = createJsonFrom(expectedAuction);

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

    @Test
    public void
    post_to_bid_an_existing_auction() throws JSONException {
        var givenAuctionId = givingAnExistingAuction().id;
        var expectedBid = new Bid(money(50));

        given().
                when().
                    body(createBidJsonFrom(expectedBid)).
                    post("auction/{id}/bid", givenAuctionId).
                then().
                    assertThat().
                    statusCode(201).
                    body(equalTo("OK"));

        Auction actualAuction = auctionRepository().retrieveById(givenAuctionId);
        assertThat(actualAuction.bids).hasSize(1);
        assertThatBid(actualAuction.topBid().get()).isEqualTo(expectedBid);
    }

    private String createBidJsonFrom(Bid bid) throws JSONException {
        return new JSONObject()
                .put("amount", bid.money.amount)
                .toString();
    }

    private Auction givingAnExistingAuction() {
        var expectedAuction = anAuction().build();
        this.auctionRepository.save(expectedAuction);
        return expectedAuction;
    }

    private JSONObject createJsonFrom(Auction expectedAuction) throws JSONException {
        return new JSONObject()
                .put("item", new JSONObject()
                        .put("name", expectedAuction.item.name)
                        .put("description", expectedAuction.item.description)
                )
                .put("initial_bid", expectedAuction.startingPrice.amount)
                .put("conquer_price", expectedAuction.conquerPrice.amount)
                .put("expiration_date", expectedAuction.expirationDate.toString())
                .put("minimum_overbidding_price", expectedAuction.minimumOverbiddingPrice.amount);
    }
}
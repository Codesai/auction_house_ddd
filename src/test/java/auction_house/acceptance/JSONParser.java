package auction_house.acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import org.json.JSONException;
import org.json.JSONObject;

import static java.util.stream.Collectors.toList;

public class JSONParser {
    static JSONObject createJsonFrom(Auction expectedAuction) throws JSONException {
        return new JSONObject()
                .put("item", new JSONObject()
                        .put("name", expectedAuction.item.name)
                        .put("description", expectedAuction.item.description)
                )
                .put("initial_bid", expectedAuction.startingPrice.amount)
                .put("conquer_price", expectedAuction.conquerPrice.amount)
                .put("bids", expectedAuction.bids.stream().map(JSONParser::createBidJsonFrom).collect(toList()))
                .put("expiration_date", expectedAuction.expirationDate.toString())
                .put("owner_id", expectedAuction.ownerId.id);
    }

    static String createBidJsonFrom(Bid bid) {
        try {
            return new JSONObject()
                    .put("id", bid.id)
                    .put("amount", bid.money.amount)
                    .put("bidder_id", bid.bidderId.id)
                    .toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

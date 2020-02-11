package acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    static JSONObject createJsonFrom(Auction expectedAuction) throws JSONException {
        return new JSONObject()
                .put("item", new JSONObject()
                        .put("name", expectedAuction.item.name)
                        .put("description", expectedAuction.item.description)
                )
                .put("initial_bid", expectedAuction.startingPrice.amount)
                .put("conquer_price", expectedAuction.conquerPrice.amount)
                .put("expiration_date", expectedAuction.expirationDate.toString())
                .put("minimum_overbidding_price", expectedAuction.minimumOverbiddingPrice.amount)
                .put("owner", expectedAuction.ownerId.id);
    }

    static String createBidJsonFrom(Bid bid) throws JSONException {
        return new JSONObject()
                .put("amount", bid.money.amount)
                .put("bidder_id", bid.bidderId.id)
                .toString();
    }
}

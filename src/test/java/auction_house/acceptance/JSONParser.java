package auction_house.acceptance;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public class JSONParser {
    static JSONObject createAuctionJsonFrom(String name, String description, double initialBidAmount, double conquerPriceAmount, List<String> bids, LocalDate expirationDay, String ownerId) throws JSONException {
        return new JSONObject()
                .put("item", new JSONObject()
                        .put("name", name)
                        .put("description", description)
                )
                .put("initial_bid", initialBidAmount)
                .put("conquer_price", conquerPriceAmount)
                .put("bids", bids)
                .put("expiration_date", expirationDay.toString())
                .put("owner_id", ownerId);
    }

    static String createBidJsonFrom(String auctionId, double amount, String bidderId) {
        try {
            return new JSONObject()
                    .put("id", auctionId)
                    .put("amount", amount)
                    .put("bidder_id", bidderId)
                    .toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

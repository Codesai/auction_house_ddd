package auction_house.acceptance;

import com.codesai.auction_house.infrastructure.api.dtos.AuctionDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

import static java.util.Collections.emptyList;

public class JSONParser {
    static JsonObject createAuctionJsonFrom(String name, String description, double initialBidAmount, double conquerPriceAmount, LocalDate expirationDay, String ownerId) throws JSONException {
        return new Gson().toJsonTree(new AuctionDTO(
                name,
                description,
                initialBidAmount,
                conquerPriceAmount,
                emptyList(),
                expirationDay.toString(),
                ownerId
        )).getAsJsonObject();
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

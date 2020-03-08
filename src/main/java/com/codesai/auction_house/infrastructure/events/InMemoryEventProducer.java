package com.codesai.auction_house.infrastructure.events;

import com.codesai.EventStore;
import com.codesai.auction_house.business.model.auction.EventProducer;
import com.codesai.auction_house.business.model.auction.events.DeclareWinnerEvent;
import org.json.JSONException;
import org.json.JSONObject;


public class InMemoryEventProducer implements EventProducer {

    @Override
    public void produce(DeclareWinnerEvent declareWinnerEvent) {
        var declareWinnerEventAsJson = asJson(declareWinnerEvent);
        EventStore.events.add(declareWinnerEventAsJson);
        System.out.println(String.format("event publish: %s", declareWinnerEventAsJson));
    }

    private static String asJson(DeclareWinnerEvent declareWinnerEvent) {
        try {
            return new JSONObject()
                    .put("eventType", declareWinnerEvent.getClass().getSimpleName())
                    .put("winner", declareWinnerEvent.winner.id)
                    .put("auctionId", declareWinnerEvent.auctionId)
                    .put("bidWinnerAmount", declareWinnerEvent.bidWinnerAmount.amount)
                    .toString();
        } catch (JSONException e) {
            throw new RuntimeException("cannot create json for event", e);
        }
    }
}

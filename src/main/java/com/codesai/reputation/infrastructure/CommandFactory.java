package com.codesai.reputation.infrastructure;

import com.codesai.reputation.business.actions.commands.Command;
import com.codesai.reputation.business.actions.commands.UserReputationForAuctionWinnerCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CommandFactory {
    public static Command createCommandFrom(String event) {
        var bodyAsJson = new Gson().fromJson(event, JsonObject.class);
        if (bodyAsJson.get("eventType").getAsString().equals("DeclareWinnerEvent")) {
            return new UserReputationForAuctionWinnerCommand(
                    bodyAsJson.get("winner").getAsString(),
                    bodyAsJson.get("bidWinnerAmount").getAsDouble()
            );
        }
        return new Command() {};
    }
}

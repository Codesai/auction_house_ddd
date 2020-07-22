package com.codesai.reputation.infrastructure;

import com.codesai.EventStore;

public class Reputation {
    public static void start() {
        System.out.println("starting reputation bounded context");
        EventStore.listen(Reputation::processEvent);
    }

    public static void processEvent(String event) {
        System.out.println(String.format("Event received %s", event));
        var command = CommandFactory.createCommandFrom(event);
        CommandDispatcher.executeActionFor(command);
    }

}

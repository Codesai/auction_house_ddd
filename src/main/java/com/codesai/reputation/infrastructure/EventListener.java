package com.codesai.reputation.infrastructure;

import com.codesai.EventStore;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class EventListener {

    public void listen(Consumer<String> nextEvent) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!EventStore.events.isEmpty()) {
                    nextEvent.accept(EventStore.events.get(0));
                    EventStore.events.remove(0);
                }
            }
        }, 0, 5000);
    }

}

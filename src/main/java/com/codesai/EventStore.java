package com.codesai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventStore {
    public static List<Consumer<String>> consumers = new ArrayList<>();

    public static void add(String event) {
        consumers.stream().forEach(c -> c.accept(event));
    }

    public static void listen(Consumer<String> consumer) {
        consumers.add(consumer);
    }
}


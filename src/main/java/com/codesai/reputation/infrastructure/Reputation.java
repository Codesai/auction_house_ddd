package com.codesai.reputation.infrastructure;

public class Reputation {
    public static void start() {
        System.out.println("starting reputation bounded context");
        new EventListener().listen(s -> {
            System.out.println(String.format("Event received %s", s));
        });
    }
}

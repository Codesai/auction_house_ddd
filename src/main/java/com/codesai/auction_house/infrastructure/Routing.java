package com.codesai.auction_house.infrastructure;

import spark.Spark;

import static spark.Spark.get;

public class Routing {

    public final static Integer PORT = 9001;

    public static void Routes() {
        Spark.port(PORT);
        get("/status", (req, res) -> "OK");
    }
}

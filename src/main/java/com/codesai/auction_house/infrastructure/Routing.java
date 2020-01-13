package com.codesai.auction_house.infrastructure;

import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.post;
public class Routing {

    public final static Integer PORT = 9001;

    public static void Routes() {
        Spark.port(PORT);

        get("/status", (req, res) -> "OK");

        post("/auction", (req, res) -> {
            res.status(201);
            res.header("Location", "http://localhost:9001/auction/anyItem");
            return res;
        });
    }
}

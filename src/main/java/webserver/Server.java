package webserver;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class Server {
    public static void main(String args[]) {

        //Spark config
        port(80);
        staticFiles.location("/public/frontend");
        staticFiles.expireTime(600L);

        //Routes
        get("/", (req, res) -> {
            res.redirect("/pages/index.js");
            return null;
        });
        get("*", (req, res) -> {
            res.redirect("/pages/404.js");
            return null;
        });

    }
}

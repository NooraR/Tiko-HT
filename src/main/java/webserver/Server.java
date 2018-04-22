package webserver;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class Server {
    public static void main(String args[]) {

        //Spark config
        port(80);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        //Routes
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });
        get("*", (req, res) -> {
            res.redirect("/notfound.html");
            return null;
        });

    }
}

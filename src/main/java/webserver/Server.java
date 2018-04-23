package webserver;

import database.HibernateConfiguration;
import org.hibernate.SessionFactory;
import webserver.controllers.PublicController;
import webserver.controllers.UserController;

import static spark.Spark.*;

public class Server {
    public static void main(String args[]) {

        //Load up database interface
        SessionFactory sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();

        //Spark config
        port(80);
        staticFiles.location("/frontend/public");
        staticFiles.expireTime(600L);

        //Routes
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        get("/data/listWorks", (req, res) -> PublicController.listWorks(req, res, sessionFactory));

        get("*", (req, res) -> {
            res.status(404);
            res.redirect("/notfound.html");
            return null;
        });

        post("/register", (req, res) -> UserController.register(req, res, sessionFactory));

        post("/login", (req, res) -> UserController.login(req, res, sessionFactory));

    }
}

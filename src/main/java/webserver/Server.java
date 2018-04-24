package webserver;

import database.HibernateConfiguration;
import database.OrderHandler;
import org.hibernate.SessionFactory;
import webserver.controllers.OrderController;
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

        get("/data/works", (req, res) -> PublicController.getWorksAvailable(req, res, sessionFactory));

        post("/register", (req, res) -> UserController.register(req, res, sessionFactory));

        post("/login", (req, res) -> UserController.login(req, res, sessionFactory));

        post("/order", (req, res) -> OrderController.createOrder(req, res, sessionFactory));

        get("/order/confirm", (req, res) -> OrderController.confirmOrder(req, res, sessionFactory));

        get("/order/cancel", (req, res) -> OrderController.cancelOrder(req, res, sessionFactory));


        //Set 404
        get("*", (req, res) -> {
            res.status(404);
            res.redirect("/notfound.html");
            return null;
        });

        //Free up reserved products in DB on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Clearing reservations");
            try {
                OrderHandler handler = new OrderHandler(sessionFactory);
                handler.freeAllReservations();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }));

    }
}

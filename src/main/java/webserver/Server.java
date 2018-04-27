package webserver;

import database.HibernateConfiguration;
import database.OrderHandler;
import org.hibernate.SessionFactory;
import webserver.controllers.ManagementController;
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
        //staticFiles.location("/frontend/src");
        String directory = System.getProperty("user.dir") + "/src/main/resources/public";
        staticFiles.externalLocation(directory);
        System.out.println("Reading files from " + directory);

        //Routes
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        //Public data
        get("/api/data/works", (req, res) -> PublicController.getWorksAvailable(req, res, sessionFactory));

        get("/api/data/products", (req, res) -> PublicController.getProductsAvailable(req, res, sessionFactory));

        //User handling
        post("/register", (req, res) -> UserController.register(req, res, sessionFactory));

        post("/login", (req, res) -> UserController.login(req, res, sessionFactory));

        get("/logout", (req, res) -> UserController.logout(req, res));

        //Order handling
        post("/api/order", (req, res) -> OrderController.createOrder(req, res, sessionFactory));

        get("/api/order/confirm", (req, res) -> OrderController.confirmOrder(req, res, sessionFactory));

        get("/api/order/cancel", (req, res) -> OrderController.cancelOrder(req, res, sessionFactory));

        //"Management" handling
        before("/api/management/*", (req, res) -> ManagementController.checkUserPermissions(req, res));
        post("/api/management/product/add", (req, res) -> ManagementController.addProduct(req, res, sessionFactory));

        get("/api/management/reports/works", (req, res) -> new ManagementController().getWorkReport(req, res, sessionFactory));

        get("/api/management/reports/users", (req, res) -> new ManagementController().getUserSalesReport(req, res, sessionFactory));



        //Set 404 routes
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

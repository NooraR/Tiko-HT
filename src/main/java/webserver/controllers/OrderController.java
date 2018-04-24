package webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import database.OrderHandler;
import datamodel.Order;
import datamodel.User;
import datamodel.Work;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import webserver.util.Reply;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class OrderController {
    public static String createOrder(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        OrderHandler handler = new OrderHandler(sessionFactory);
        //Get user from session data attributes
        User user = req.session().attribute("user");

        if(user != null && req.session().attribute("order") == null) {
            try {
                List<Work> works = gson.fromJson(req.body(), new TypeToken<List<Work>>(){}.getType());

                Order order = handler.createReservation(user, works);
                req.session().attribute("order", order);

                res.status(200);
                return gson.toJson(new Reply(true, "Products reserved for order", order));
            } catch (Exception e) {
                System.err.println("Failed to reserve order: " + e.getMessage());

                res.status(400);
                return gson.toJson(new Reply(false, "Failed to create reservation", null));
            }
        } else {
            res.status(400);
            return gson.toJson(new Reply(false, "Failed to get session data", null));
        }
    }

    public static String confirmOrder(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        OrderHandler handler = new OrderHandler(sessionFactory);

        if(req.session() != null && req.session().attribute("order") != null) {
            try {
                Order order = req.session().attribute("order");
                handler.confirmOrder(order.getId());

                //Clear order from session data now that its processing complete
                req.session().attribute("order", null);

                res.status(200);
                return gson.toJson(new Reply(true, "Order confirmed", order));
            } catch (EntityNotFoundException e) {
                res.status(400);
                return gson.toJson(new Reply(false, "Couldn't find order", null));
            } catch (Exception e) {
                System.err.println("Failed to confirm an order: " + e.getMessage());
                res.status(400);
                return gson.toJson(new Reply(false, "Failed to confirm order", null));
            }
        } else {
            res.status(400);
            return gson.toJson(new Reply(false, "Failed to get session data", null));
        }
    }

    public static String cancelOrder(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        OrderHandler handler = new OrderHandler(sessionFactory);

        if(req.session() != null && req.session().attribute("order") != null) {
            try {
                Order order = req.session().attribute("order");
                handler.freeReservation(order.getId());

                //Clear order from session data now that its processing is complete
                req.session().attribute("order", null);

                res.status(200);
                return gson.toJson(new Reply(true, "Order cancelled", order));
            } catch (EntityNotFoundException e) {
                res.status(400);
                return gson.toJson(new Reply(false, "Couldn't find order", null));
            } catch (Exception e) {
                res.status(400);
                System.err.println("Failed to cancel an order: " + e.getMessage());
                return gson.toJson(new Reply(false, "Failed to cancel order", null));
            }
        } else {
            res.status(400);
            return gson.toJson(new Reply(false, "Failed to get session data", null));
        }
    }
}

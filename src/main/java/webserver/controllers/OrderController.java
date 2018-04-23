package webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import database.OrderHandler;
import datamodel.Order;
import datamodel.User;
import datamodel.Work;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import webserver.util.Reply;

import java.util.Date;
import java.util.List;

public class OrderController {
    public static String createOrder(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new Gson();
        OrderHandler handler = new OrderHandler(sessionFactory);
        //Get user from session data attributes
        User user = req.session().attribute("user");

        if(user != null) {
            try {
                List<Work> works = gson.fromJson(req.body(), new TypeToken<List<Work>>(){}.getType());

                Order order = handler.createReservation(user, works);

                res.status(200);
                return gson.toJson(new Reply(true, "Products reserved for order", null));
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
}

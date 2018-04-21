package webserver;

import com.google.gson.Gson;
import database.UserHandler;
import datamodel.User;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

public class UserController {
    public static String register(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new Gson();
        String json = null;

        try {
            UserHandler handler = new UserHandler(sessionFactory);

            User user = gson.fromJson(req.body(), User.class);
            handler.addUser(user);

            json = gson.toJson(new Object() {
                boolean success = true;
                String username = user.getEmail();
            });
        } catch (Exception e) {
            json = gson.toJson(new Object() {
               boolean success = false;
               String error = "Failed to add user";
            });

            System.err.println("Error while creating new user from json:" + req.body() + "\nError: " + e.getMessage());
        }
        return json;
    }
}

package webserver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import database.UserHandler;
import datamodel.User;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

import javax.persistence.EntityExistsException;

public class UserController {
    public static String register(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new Gson();
        String json = null;

        res.type("application/json");

        try {
            UserHandler handler = new UserHandler(sessionFactory);

            User user = gson.fromJson(req.body(), User.class);
            handler.addUser(user);

            json = "{" +
                        "\"success\": true, " +
                        "\"email\": \"" + user.getEmail() + "\"" +
                    "}";
            res.status(200);
        } catch (EntityExistsException e) {
            json = "{" +
                        "\"success\": false, " +
                        "\"error\": \"Email is already registered\"" +
                    "}";
            res.status(400);
        } catch (Exception e) {
            json = "{" +
                        "\"success\": false, " +
                        "\"error\": \"Sorry, could not register user.\"" +
                    "}";
            System.err.println("Error while creating new user from json: " + e.getMessage());
            res.status(400);
        }
        return json;
    }
}

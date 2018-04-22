package webserver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import database.UserHandler;
import datamodel.User;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import webserver.util.Reply;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public class UserController {
    public static String register(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new Gson();
        String json = null;

        res.type("application/json");

        try {
            UserHandler handler = new UserHandler(sessionFactory);

            User user = gson.fromJson(req.body(), User.class);
            handler.addUser(user);

            json = gson.toJson(new Reply(true, "Registered successfully", user.getEmail()));
            res.status(200);
        } catch (EntityExistsException e) {
            json = gson.toJson(new Reply(false, "This email is already registered.", null));
            res.status(400);
        } catch (Exception e) {
            json = gson.toJson(new Reply(false, "Failed to register. Please try again.", null));
            System.err.println("Error while creating new user from json: " + e.getMessage());
            res.status(400);
        }
        return json;
    }

    public static String login(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new Gson();

        res.type("application/json");

        try {
            UserHandler handler = new UserHandler(sessionFactory);
            Credentials credentials = gson.fromJson(req.body(), Credentials.class);

            User userFromDB = handler.getUserByEmail(credentials.email);

            if(userFromDB.getPassword().equals(credentials.password)) {
                //Create a session
                req.session(true);
                req.session().attribute("user", userFromDB);
                res.status(200);
                userFromDB.setPassword(null);
                return gson.toJson(new Reply(true, "Logged in successfully", userFromDB));
            } else {
                //Wrong password
                res.status(400);
                return gson.toJson(new Reply(false, "Wrong email or password", null));
            }
        } catch (EntityNotFoundException e) {
            //User not found in the db
            res.status(400);
            return gson.toJson(new Reply(false, "Wrong email or password", null));
        } catch (Exception e) {
            res.status(400);
            return gson.toJson(new Reply(false, "Something went wrong while attempting login. Please try again.", null));
        }
    }
}

class Credentials {
    String email;
    String password;

    public Credentials() {
        email = null;
        password = null;
    }
}

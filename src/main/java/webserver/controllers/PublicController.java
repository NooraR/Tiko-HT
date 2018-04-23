package webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.WorkHandler;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import webserver.util.Reply;

import java.util.List;

public class PublicController {
    public static String getWorksAvailable(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = null;
        try {
            WorkHandler handler = new WorkHandler(sessionFactory);
            List works = handler.getWorksAvailable();

            json = gson.toJson(new Reply(true, "Successfully fetched works", works));

            res.type("application/json");
            res.status(200);
        } catch (Exception e) {
            json = gson.toJson(new Reply(false, "Failed to fetch works", null));
        }
        return json;
    }
}

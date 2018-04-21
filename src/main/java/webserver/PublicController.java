package webserver;

import com.google.gson.Gson;
import database.WorkHandler;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

import java.util.List;

public class PublicController {
    public static String listWorks(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new Gson();
        String json = null;
        try {
            WorkHandler handler = new WorkHandler(sessionFactory);
            List works = handler.getWorksAvailable();

            json = gson.toJson(works);

            res.type("application/json");
            res.status(200);
        } catch (Exception e) {
            json = gson.toJson(new Error() {
                String error = "Failed to fetch works: " + e.getMessage();
            });
        }
        return json;
    }
}

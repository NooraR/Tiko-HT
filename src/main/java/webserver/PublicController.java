package webserver;

import com.google.gson.Gson;
import database.QueryHandler;
import datamodel.Work;
import spark.Request;
import spark.Response;

import java.util.List;

public class PublicController {
    public static String listWorks(Request req, Response res) {
        Gson gson = new Gson();
        String json = null;
        try {
            QueryHandler handler = new QueryHandler();
            List<Work> works = handler.getAllWorks();

            json = gson.toJson(works);
        } catch (Exception e) {
            json = gson.toJson(new Error() {
                String error = "Failed to fetch works: " + e.getMessage();
            });
        }
        return json;
    }
}

package webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.AntiquarianHandler;
import database.ProductHandler;
import database.WorkHandler;
import datamodel.Antiquarian;
import datamodel.Product;
import datamodel.Work;
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
            List<Work> works = handler.getWorksAvailable();

            json = gson.toJson(new Reply(true, "Successfully fetched works", works));

            res.type("application/json");
            res.status(200);
        } catch (Exception e) {
            System.out.println(e);
            json = gson.toJson(new Reply(false, "Failed to fetch works", null));
        }
        return json;
    }

    public static String getProductsAvailable(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ProductHandler handler = new ProductHandler(sessionFactory);

        res.type("application/json");
        try {
            List<Product> products = handler.getProductsByStatus("FREE");

            res.status(200);
            return gson.toJson(new Reply(true, "Retrieved products", products));
        } catch (Exception e) {
            res.status(400);
            return gson.toJson(new Reply(false, "Failed to retrieve products", null));
        }
    }

    public static String getAntiquaries(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        AntiquarianHandler handler = new AntiquarianHandler(sessionFactory);

        res.type("application/json");

        try {
            List<Antiquarian> antiquaries = handler.getAntiquaries();
            res.status(200);

            return gson.toJson(new Reply(true, "Retrieved antiquaries", antiquaries));
        } catch (Exception e) {
            res.status(400);
            return gson.toJson(new Reply(false, "Failed to retrieve antiquaries", null));
        }
    }
}

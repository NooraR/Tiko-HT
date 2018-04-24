package webserver.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.ProductHandler;
import datamodel.Product;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import webserver.util.Reply;

import javax.persistence.EntityExistsException;

public class ManagementController {
    public static String addProduct(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ProductHandler handler = new ProductHandler(sessionFactory);

        try {
            Product product = gson.fromJson(req.body(), Product.class);

            int productId = handler.addProduct(product);

            res.status(200);
            return gson.toJson(new Reply(true, "Added a new product", productId));
        } catch (EntityExistsException e) {
            return gson.toJson(new Reply(false, "Product already exists", null));
        } catch (Exception e) {
            System.err.println("Failed to add a new product" + e.getMessage());
            return gson.toJson(new Reply(false, "Failed to add the product", null));
        }
    }
}

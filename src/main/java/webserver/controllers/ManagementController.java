package webserver.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.ProductHandler;
import datamodel.Product;
import datamodel.User;
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

            product = handler.addProduct(product);

            res.status(200);
            return gson.toJson(new Reply(true, "Added a new product", product));
        } catch (EntityExistsException e) {
            res.status(400);
            return gson.toJson(new Reply(false, "Product already exists", null));
        } catch (Exception e) {
            res.status(400);
            System.err.println("Failed to add a new product" + e.getMessage());
            return gson.toJson(new Reply(false, "Failed to add the product", null));
        }
    }

    public static String checkUserPermissions(Request req, Response res){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try{
            if (req.session() != null && req.session().attribute("user") != null){
                User user = req.session().attribute("user");
                String msg = "User "+user.getFirstName()+" "+user.getLastName()+" admin status: "+user.isAdmin();
                System.out.println("Permission checked succesfully Result = "+user.isAdmin());
                return gson.toJson(new Reply(user.isAdmin(), msg, null));
            }
            else
                throw new Exception("Either the session or the user is null");
        } catch (Exception e){
            System.err.println("Exception when checking permission " + e.getMessage());
            return gson.toJson(new Reply(false, "Failed to check user permission.", null));
        }
    }
}

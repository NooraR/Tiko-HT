package webserver.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import database.ProductHandler;
import datamodel.Product;
import datamodel.Work;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import spark.Request;
import spark.Response;
import webserver.util.Reply;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

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

    public String getWorkReport(Request req, Response res, SessionFactory sessionFactory) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("SELECT DISTINCT genre FROM Work");
            List<String> genres = (List<String>) query.list();

            //Init list to return as report
            List<WorkReportListObject> report = new ArrayList<>();

            for(String genre : genres) {
                query = session.createQuery("FROM Work WHERE genre = :genre");
                query.setParameter("genre", genre);
                List<Work> worksOfGenre = (List<Work>) query.list();

                double totalPrices = 0;
                int numberOfProducts = 0;
                //Go through works in this genre
                for(Work work : worksOfGenre) {
                    //Go through every product that a work has
                    for(Product product : work.getProducts()) {
                        totalPrices += product.getSellingPrice();
                        numberOfProducts++;
                    }
                }

                //Form object and add it to the list to be formed into json
                WorkReportListObject listObject = new WorkReportListObject();
                listObject.genre = genre;
                listObject.totalSellingPrice = totalPrices;
                if(numberOfProducts > 0) {
                    listObject.averagePrice = totalPrices / (double) numberOfProducts;
                }
                report.add(listObject);
            }

            session.getTransaction().commit();

            return gson.toJson(new Reply(true, "Successfully formed report", report));
        } catch (Exception e) {
            session.getTransaction().rollback();
            return gson.toJson(new Reply(false, "Failed to fetch report data", null));
        } finally {
            session.close();
        }
    }

    /**
     * Helper class for producing a report list in json.
     */
    class WorkReportListObject {
        @Expose
        public String genre;

        @Expose
        public double totalSellingPrice;

        @Expose
        public double averagePrice;

        public WorkReportListObject() {
            genre = null;
            totalSellingPrice = 0;
            averagePrice = 0;
        }
    }
}

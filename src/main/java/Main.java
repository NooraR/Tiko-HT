import database.ProductHandler;
import database.UserHandler;
import datamodel.Product;
import datamodel.User;

import java.util.List;

public class Main {

    public static void main(String args[]) {
        try {
            UserHandler handler = new UserHandler();
            User user = handler.getUserById(1);

            System.out.println("Hei " + user.getFirstName() + " " + user.getLastName() + "!");
            System.out.println("Sähköpostiosoitteesi on: "+user.getEmail() +" ja osoitteesi: "+user.getAddress());


            ProductHandler productHandler = new ProductHandler();
            List<Product> products = productHandler.getProductByStatus("FREE");

            for (Product pro: products){
                System.out.println(pro.getStatus());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
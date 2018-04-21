import database.ProductHandler;
import database.UserHandler;
import datamodel.Product;
import datamodel.User;

public class Main {

    public static void main(String args[]) {
        try {
            UserHandler handler = new UserHandler();
            User user = handler.getUserById(1);

            System.out.println("Hei " + user.getFirstName() + " " + user.getLastName() + "!");
            System.out.println("Sähköpostiosoitteesi on: "+user.getEmail() +" ja osoitteesi: "+user.getAddress());


            ProductHandler productHandler = new ProductHandler();
            Product product = productHandler.getProductById(Product.product_status.FREE);

            System.out.println(product.getSellingPrice());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
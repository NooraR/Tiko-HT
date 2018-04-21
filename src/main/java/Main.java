import database.UserHandler;
import datamodel.User;

public class Main {

    public static void main(String args[]) {
        try {
            UserHandler handler = new UserHandler();
            User user = handler.getUserById(1);

            System.out.println("Hei " + user.getFirstName() + " " + user.getLastName() + "!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
public class Main {




    public static void main(String args[]) {
        System.out.println("Hello! Connecting to DB...");
        ConnectionHandler connection = new ConnectionHandler("tiko", "t1k0");
        try{
            connection.connect();
        } catch (Exception e){
            System.out.println(e);
        }


    }


}
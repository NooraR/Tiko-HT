import java.sql.*;

class ConnectionHandler {

    private String user;
    private String password;

    ConnectionHandler(String user, String password){
        if (user != null)
            this.user = user;

        if (password != null)
            this.password = password;
    }

    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            String url = "jdbc:postgresql://51.255.172.150/tikoht";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, PreparedStatement ps, ResultSet rs){
        try{
            ps.close();
            rs.close();
            connection.close();
        } catch (Exception e){
            System.err.println("Error while closing connection.");
        }
    }
}

package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ConnectionHandler {

    private String user;
    private String password;

    ConnectionHandler(){
        List<String> settings = readSettingsFile();
        if(!settings.get(0).equals("Error")) {
            user = settings.get(0);
            password = settings.get(1);
        } else {
            user = null;
            password = null;
        }
    }

    public List<String> readSettingsFile() {
        try {
            String path = Paths.get("").toAbsolutePath().normalize().toString() + "\\settings";
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            List<String> error = new ArrayList<String>();
            error.add("Error");
            return error;
        }
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
            if(ps != null) {
                ps.close();
            }

            if(rs != null) {
                rs.close();
            }

            if(connection != null) {
                connection.close();
            }
        } catch (Exception e){
            System.err.println("Error while closing connection.");
        }
    }
}

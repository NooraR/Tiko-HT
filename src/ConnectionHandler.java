import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.*;

class ConnectionHandler {

    private String user;
    private String password;

    ConnectionHandler(String user, String password){
        if (user != null)
            this.user = user;

        if (password != null)
            this.password = password;
    }

    public Connection connect() throws ClassNotFoundException {
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

    public void closeConnection(Connection connection){
        try{
            connection.close();
        } catch (Exception e){
            System.err.println("Error while closing connection.");
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList = null;

        try {
            connection = connect();
            ps = connection.prepareStatement("SELECT * FROM userAccount;");
            rs = ps.executeQuery();

            userList = new ArrayList<User>();
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                userList.add(user);
            }

            return userList;
        } catch(Exception e) {
            System.err.println("Error while generating List<User>!");
        } finally {
            ps.close();
            rs.close();
            connection.close();
        }
        return null;
    }

    public List<Work> getAllWorks() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Work> workList = null;

        try {
            connection = connect();
            ps = connection.prepareStatement("SELECT * FROM useraccount;");
            rs = ps.executeQuery();

            workList = new ArrayList<Work>();
            while(rs.next()) {
                Work work = new Work();
                work.setId(rs.getInt("id"));
                work.setAuthor(rs.getString("author"));
                work.setName(rs.getString("name"));
                work.setIsbn(rs.getString("isbn"));
                work.setPublished(rs.getInt("published"));
                work.setGenre(rs.getString("genre"));
                work.setType(rs.getString("type"));
                work.setWeight(rs.getDouble("weight"));

                workList.add(work);
            }

            return workList;
        } catch(Exception e) {
            System.err.println("Error while generating List<User>!");
        } finally {
            ps.close();
            rs.close();
            connection.close();
        }
        return null;
    }

}

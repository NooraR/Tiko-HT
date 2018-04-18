import datamodel.User;
import datamodel.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {
    ConnectionHandler con;

    public QueryHandler() {
        con = new ConnectionHandler();
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList = null;

        try {
            connection = con.getConnection();
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
            con.closeConnection(connection, ps, rs);
        }
        return null;
    }

    public List<Work> getAllWorks() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Work> workList = null;

        try {
            connection = con.getConnection();
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
                work.setBalance(getBalanceForWork(work));
                workList.add(work);
            }

            return workList;
        } catch(Exception e) {
            System.err.println("Error while generating List<User>!");
        } finally {
            con.closeConnection(connection, ps, rs);
        }
        return null;
    }

    public int getBalanceForWork(Work work) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int balance = 0;

        try {
            connection = con.getConnection();
            ps = connection.prepareStatement("SELECT COUNT(id) AS balance FROM product WHERE workid = ? AND status = 'free'");
            ps.setInt(1, work.getId());

            rs = ps.executeQuery();
            if(rs.next()) {
                balance = rs.getInt("balance");
            }
        } catch (Exception e) {
            throw new Exception("Failed to get balance for work");
        } finally {
            con.closeConnection(connection, ps, rs);
        }
        return balance;
    }
}

package database;

import datamodel.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserHandler {
    private ConnectionHandler con;

    public UserHandler() {
        con = new ConnectionHandler();
    }


    /**
     * Checks if an user exists in the DB.
     * @param identifier either user id, email or User object
     * @return boolean
     * @throws Exception if something went wrong
     */
    public boolean userExists(Object identifier) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean exists = true;

        try {
            connection = con.getConnection();

            //Check if trying to get using id or email
            if(identifier instanceof Integer) {
                ps = connection.prepareStatement("SELECT * FROM useraccount WHERE id = ?");
                ps.setInt(1, (Integer) identifier);
            } else if(identifier instanceof String) {
                ps = connection.prepareStatement("SELECT * FROM useraccount WHERE email = ?");
                ps.setString(1, (String) identifier);
            } else if(identifier instanceof  User) {
                ps = connection.prepareStatement("SELECT * FROM useraccount WHERE id = ?");
                ps.setInt(1, ((User) identifier).getId());
            } else {
                throw new Exception("Identifier not an id nor email");
            }

            rs = ps.executeQuery();
            exists = rs.isBeforeFirst();
        } catch (Exception e) {
            throw new Exception("Failed to check if user exists: " + e.getMessage());
        } finally {
            con.closeConnection(connection, ps, rs);
        }

        return exists;
    }

    /**
     * Fetch an user's data from db.
     * @param identifier either the user's id or email
     * @return User object with data
     * @throws Exception if something went wrong
     */
    public User getUser(Object identifier) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = con.getConnection();

            //Check if trying to get using id or email
            if(identifier instanceof Integer) {
                ps = connection.prepareStatement("SELECT id, first_name, last_name, address, email, phone_number FROM useraccount WHERE id = ?");
                ps.setInt(1, (Integer) identifier);
            } else if(identifier instanceof String) {
                ps = connection.prepareStatement("SELECT id, first_name, last_name, address, email, phone_number FROM useraccount WHERE email = ?");
                ps.setString(1, (String) identifier);
            } else {
                throw new Exception("Identifier not an id nor email");
            }

            rs = ps.executeQuery();

            if(rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                return user;
            } else {
                throw new Exception("User doesn't exist");
            }
        } catch (Exception e) {
            throw new Exception("Failed to fetch user from db: " + e.getMessage());
        } finally {
            con.closeConnection(connection, ps, rs);
        }
    }

    /**
     * Adds a new user into the database.
     * @param user User object
     * @return boolean telling if succeeded
     * @throws Exception if something went wrong
     */
    public boolean addUser(User user) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            if(userExists(user.getEmail())) {
                connection = con.getConnection();

                ps = connection.prepareStatement(
                        "INSERT INTO useraccount (first_name, last_name, address, email, password, phone_number) " +
                                "VALUES (?, ?, ?, ?, ?, ?)"
                );
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getAddress());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPassword());
                ps.setString(6, user.getPhoneNumber());

                return ps.executeUpdate() > 0;
            } else {
                throw new Exception("User already exists");
            }
        } catch (Exception e) {
            throw new Exception("Failed to add new user to db: " + e.getMessage());
        } finally {
            con.closeConnection(connection, ps, null);
        }
    }
}

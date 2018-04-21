package database;

import datamodel.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserHandler {
    private SessionFactory sessionFactory;

    public UserHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public User getUserById(int id) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);

            session.getTransaction().commit();

            return user;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        }
    }

}

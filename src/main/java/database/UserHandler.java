package database;

import datamodel.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public class UserHandler {
    private SessionFactory sessionFactory;

    public UserHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public UserHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User getUserById(int id) throws Exception {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);

            session.getTransaction().commit();

            return user;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public User getUserByEmail(String email) throws Exception {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM User WHERE email=:email");
            query.setParameter("email", email);

            User user = (User) query.uniqueResult();
            session.getTransaction().commit();

            return user;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new EntityNotFoundException("Could not retrieve user: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public int addUser(User user) throws Exception {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE email=:email");
            query.setParameter("email", user.getEmail());

            int userId = -1;
            if(query.uniqueResult() == null) {
                userId = (Integer) session.save(user);
            } else {
                throw new EntityExistsException("User already exists.");
            }

            session.getTransaction().commit();

            return userId;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Adding new user failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}

package database;

import datamodel.Antiquarian;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class AntiquarianHandler {
    private SessionFactory sessionFactory;

    public AntiquarianHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Antiquarian getAntiquaryById(int id) throws HibernateException {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Antiquarian antiquary = session.get(Antiquarian.class, id);
            session.getTransaction().commit();

            return antiquary;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to fetch antiquary: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Antiquarian> getAntiquaries() throws HibernateException {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Antiquarian");
            List<Antiquarian> antiquaries = (List<Antiquarian>) query.list();

            session.getTransaction().commit();
            return antiquaries;
        } catch (HibernateException e) {
            throw new HibernateException("Failed to fetch antiquaries: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}

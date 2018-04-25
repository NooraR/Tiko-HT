package database;

import datamodel.Antiquarian;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}

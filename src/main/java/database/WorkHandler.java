package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class WorkHandler {
    private SessionFactory sessionFactory;

    public WorkHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public WorkHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List getWorksAvailable() throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("from Work");
            List list = query.list();

            session.getTransaction().commit();

            return list;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to fetch works: " + e.getMessage());
        }
    }
}

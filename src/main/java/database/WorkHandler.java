package database;

import datamodel.Work;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.EntityExistsException;
import java.util.List;

public class WorkHandler {
    private SessionFactory sessionFactory;

    public WorkHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public WorkHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Work> getWorksAvailable() throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("from Work where balance > 0");
            List<Work> list = (List<Work>) query.list();

            session.getTransaction().commit();

            return list;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to fetch works: " + e.getMessage());
        }
    }

    public int addWork(Work work) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Query query = session.createQuery("from Work where id=:id");
            query.setParameter("id", work.getId());

            int workId = -1;
            if(query.uniqueResult() == null) {
                workId = (Integer) session.save(work);
            } else {
                throw new EntityExistsException("Work already exists in DB.");
            }

            session.getTransaction().commit();

            return workId;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Adding new user failed: " + e.getMessage());
        }
    }

}

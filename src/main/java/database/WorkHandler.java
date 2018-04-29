package database;

import datamodel.Antiquarian;
import datamodel.Work;
import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
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
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Work WHERE products.size > 0");
            List<Work> list = (List<Work>) query.list();

            session.getTransaction().commit();

            return list;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to fetch works: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public Work addWork(Work work, Session session) throws HibernateException {
        try {
            //Try to find with different attributes in case it's a new work
            Query query = session.createQuery("FROM Work WHERE id=:id OR isbn=:isbn OR (author=:author AND name=:name)");
            query.setParameter("id", work.getId());
            query.setParameter("isbn", work.getIsbn());
            query.setParameter("author", work.getAuthor());
            query.setParameter("name", work.getName());

            if(query.uniqueResult() == null) {
                Work centralVersion = getWorkFromCentral(work);
                if(centralVersion != null) {
                    //Use the version from central instead with its id
                    work = centralVersion;
                    session.replicate(work, ReplicationMode.EXCEPTION);
                } else {
                    //Doesn't exist in central, do regular save
                    session.save(work);
                }
                session.flush();
                session.refresh(work);
            } else {
                throw new EntityExistsException("Work already exists in DB.");
            }
            return work;
        } catch (HibernateException e) {
            throw new HibernateException("Adding new work failed: " + e.getMessage());
        }
    }

    public Work getWorkFromCentral(Work work) throws HibernateException {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Work WHERE id=:id OR isbn=:isbn OR (author=:author AND name=:name)");
            query.setParameter("id", work.getId());
            query.setParameter("isbn", work.getIsbn());
            query.setParameter("author", work.getAuthor());
            query.setParameter("name", work.getName());

            Work central = (Work) query.uniqueResult();
            session.getTransaction().commit();

            return central;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
}

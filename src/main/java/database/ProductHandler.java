package database;

import datamodel.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

import javax.persistence.EntityExistsException;
import java.util.List;

public class ProductHandler {
    private SessionFactory sessionFactory;

    public ProductHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public ProductHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Product> getProductsByStatus(String status) throws Exception {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            System.out.println("Started retrieving products by status: "+status);
            session.beginTransaction();

            Query query = session.createQuery("FROM Product WHERE status=:status");
            query.setParameter("status", status);
            List<Product> products = (List<Product>)query.list();

            session.getTransaction().commit();
            System.out.println("Retrieval succesful.");
            return products;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Product> getProductsByOrderId(int id) throws Exception{
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Product WHERE order=:id");
            query.setParameter("id", id);
            List<Product> products = (List<Product>)query.list();

            session.getTransaction().commit();

            return products;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public boolean setProductReserved(int id) throws Exception{
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();
        System.out.println("Started reserving product");
        try {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Product SET status = Product.RESERVED WHERE id=:id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Product is now reserved.");
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.err.println("Could not change product's status to RESERVED!" + e);
            return false;
        } finally {
            session.close();
        }
    }

    public boolean setProductUnavailable(int id) throws Exception{
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();
        System.out.println("Started making product unavailable.");
        try {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Product SET status = Product.UNAVAILABLE WHERE id=:id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Product is now unavailable.");
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.err.println("Could not change product's status to UNAVAILABLE!" + e);
            return false;
        } finally {
            session.close();
        }
    }

    public int addProduct(Product product) throws Exception {
        //Fetch antiquary info from db
        try {
            AntiquarianHandler antiquaryHandler = new AntiquarianHandler(sessionFactory);
            product.setAntiquary(antiquaryHandler.getAntiquaryById(product.getAntiquary().getId()));

            //Open session
            Session session = null;
            if(product.getAntiquary().getDbIdentifier() != null) {
                session = sessionFactory.withOptions().tenantIdentifier(product.getAntiquary().getDbIdentifier()).openSession();
            } else {
                session = sessionFactory.withOptions().tenantIdentifier("central").openSession();
            }

            try {
                session.beginTransaction();

                //Try to add the work to the db
                try {
                    WorkHandler workHandler = new WorkHandler(sessionFactory);
                    int id = workHandler.addWork(product.getWork(), session);
                    product.getWork().setId(id);
                } catch (EntityExistsException e) {
                    //Work already exists
                }

                //Add product to the db
                Query query = session.createQuery("from Product where id=:id");
                query.setParameter("id", product.getId());

                int productId = -1;
                if(query.uniqueResult() == null) {
                    productId = (Integer) session.save(product);
                } else {
                    throw new EntityExistsException("Product already exists.");
                }

                session.getTransaction().commit();

                return productId;
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new Exception("Adding new product failed: " + e.getMessage());
            } finally {
                session.close();
            }
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
}

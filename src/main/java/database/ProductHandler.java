package database;

import datamodel.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

import javax.persistence.EntityExistsException;
import java.util.Collections;
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
        Session session = sessionFactory.getCurrentSession();

        try {
            System.out.println("Started retrieving products by status: "+status);
            session.beginTransaction();

            Query query = session.createQuery("FROM Product WHERE status = '"+status+"'");
            List<Product> products = (List<Product>)query.list();

            session.getTransaction().commit();
            System.out.println("Retrieval succesful.");
            return products;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        }
    }

    public List<Product> getProductsByOrderId(int id) throws Exception{
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Product WHERE order ="+id);
            List<Product> products = (List<Product>)query.list();

            session.getTransaction().commit();

            return products;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        }
    }

    public boolean setProductReserved(int id) throws Exception{
        Session session = sessionFactory.getCurrentSession();
        System.out.println("Started reserving product");
        try {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Product SET status = 'RESERVED' WHERE id ="+id);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Product is now reserved.");
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.err.println("Could not change product's status to RESERVED!" + e);
            return false;
        }
    }

    public boolean setProductUnavailable(int id) throws Exception{
        Session session = sessionFactory.getCurrentSession();
        System.out.println("Started making product unavailable.");
        try {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Product SET status = 'UNAVAILABLE' WHERE id ="+id);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Product is now unavailable.");
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.err.println("Could not change product's status to UNAVAILABLE!" + e);
            return false;
        }
    }

    public int addProduct(Product product) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
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
            throw new Exception("Adding new user failed: " + e.getMessage());
        }
    }


}

package database;

import datamodel.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

import java.util.Collections;
import java.util.List;

public class ProductHandler {
    private SessionFactory sessionFactory;

    public ProductHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public List<Product> getProductsByStatus(String status) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Product WHERE status = '"+status+"'");
            List<Product> products = (List<Product>)query.list();

            session.getTransaction().commit();

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



}

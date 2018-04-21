package database;

import datamodel.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

public class ProductHandler {
    private SessionFactory sessionFactory;

    public ProductHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public Product getProductByStatus(Product.product_status product_status) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query query = sessionFactory.getCurrentSession().createQuery("from Product where product_status=:product_status");
            query.setParameter("product_status", product_status);

            Product product = (Product) query.uniqueResult();

            session.getTransaction().commit();

            return product;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get user by id: " + e.getMessage());
        }
    }


}

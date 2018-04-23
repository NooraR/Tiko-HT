package database;

import datamodel.Order;
import datamodel.Product;
import datamodel.User;
import datamodel.Work;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.*;

public class OrderHandler {
    private SessionFactory sessionFactory;

    public OrderHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Order createReservation(User user, List<Work> works) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();


            //Init Order
            Order order = new Order();
            order.setOrderer(user);
            order.setOrderDate(new Date());
            order.setStatus("WAITING");
            order.setProducts(new ArrayList<Product>());
            //Set order to save after changes
            session.persist(order);

            for(Work work : works) {
                for(int i = 0; i < work.getAmount(); i++) {
                    //Get an available product
                    Query query = session.createQuery("from Product WHERE work=:work AND status='FREE'");
                    query.setParameter("work", work);
                    //query.setParameter("status", Product.product_status.FREE.name());

                    Product product = (Product) query.setMaxResults(1).uniqueResult();
                    product.setOrder(order);
                    product.setStatus("RESERVED");
                    //Update that product is reserved for order
                    session.update(product);

                    order.getProducts().add(product);
                }
            }

            //Set a timer to free the reservation (after 30 minutes)
            /*order.setTimer(new Timer());
            order.getTimer().schedule(new TimerTask() {
                @Override
                public void run() {
                    freeReservation(order);
                }
            }, 30 * 1000 *60);*/

            //Send changes
            session.getTransaction().commit();

            return order;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to create reservation: " + e.getMessage());
        }
    }

    public void freeReservation(Order order) {
        //If order hasn't been confirmed
        if(!order.getStatus().equals("PROCESSED")) {
            Session session = sessionFactory.getCurrentSession();

            try {
                session.beginTransaction();

                //Free products
                for(Product product : order.getProducts()) {
                    product.setStatus("FREE");
                    product.setOrder(null);

                    session.update(product);
                }

                //Remove order
                session.delete(order);
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                System.err.println("Failed to free reservation: " + e.getMessage());
            }
        }
    }
}

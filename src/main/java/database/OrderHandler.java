package database;

import datamodel.Order;
import datamodel.Product;
import datamodel.User;
import datamodel.Work;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.EntityNotFoundException;
import java.util.*;

public class OrderHandler {
    private SessionFactory sessionFactory;

    public OrderHandler() {
        sessionFactory = new HibernateConfiguration().getConfiguration().configure().buildSessionFactory();
    }

    public OrderHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Order getOrderById(int id) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Order order = session.get(Order.class, id);

            session.getTransaction().commit();

            return order;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get order by id: " + e.getMessage());
        }
    }

    public Order createReservation(User user, List<Work> works) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();


            //Init Order
            Order order = new Order();
            order.setOrderer(user);
            order.setOrderDate(new Date());
            order.setStatus(Order.WAITING);
            order.setProducts(new ArrayList<Product>());
            //Set order to save after changes
            session.persist(order);

            for(Work work : works) {
                for(int i = 0; i < work.getAmount(); i++) {
                    //Get an available product
                    Query query = session.createQuery("from Product WHERE work=:work AND status = :status");
                    query.setParameter("work", work);
                    query.setParameter("status", Product.FREE);

                    Product product = (Product) query.setMaxResults(1).uniqueResult();
                    product.setOrder(order);
                    product.setStatus(Product.RESERVED);
                    //Update that product is reserved for order
                    session.update(product);

                    order.getProducts().add(product);
                }
            }

            //Calculate postage
            order.setPostage(calculatePostage(order.getProducts()));

            //Set a timer to free the reservation (after 30 minutes) if not confirmed by the user
            order.setTimer(new Timer());
            order.getTimer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        freeReservation(order.getId());
                    } catch (Exception e) {
                        //Order was most likely either confirmed or freed already
                    }
                }
            }, 30 * 1000 *60);

            //Send changes
            session.getTransaction().commit();

            return order;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to create reservation: " + e.getMessage());
        }
    }

    private double calculatePostage(List<Product> products) {
        double totalWeight = 0;
        for(Product product : products) {
            totalWeight += product.getWork().getWeight();
        }
        //Set up fees, first column is weight, second is fee
        double postageFees[][] = {
                {0.05, 1.40},
                {0.1, 2.10},
                {0.25, 2.80},
                {0.5, 5.60},
                {1.0, 8.40},
                {2.0, 14.00}
        };

        double totalFee = 0;

        while(totalWeight > 0) {
            //Get appropriate fee
            int i = 0;
            while(i + 1 < postageFees.length && postageFees[i][0] <= totalWeight) {
                i++;
            }
            //Change weight and total fee accordingly
            totalFee += postageFees[i][1];
            totalWeight -= postageFees[i][0];
        }

        return totalFee;
    }

    public void freeReservation(int id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Order order = session.get(Order.class, id);

            if(order != null && order.getStatus().equals(Order.WAITING)) {
                //Free products
                for (Product product : order.getProducts()) {
                    product.setStatus(Product.FREE);
                    product.setOrder(null);

                    session.update(product);
                }

                //Remove order
                session.delete(order);
                session.getTransaction().commit();
            } else {
                throw new EntityNotFoundException("Order doesn't exist or isn't in waiting state.");
            }
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to free order: " + e.getMessage());
        }
    }

    public void freeAllReservations() {
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Order WHERE status = :status");
            query.setParameter("status", Order.WAITING);
            List<Order> orders = (List<Order>) query.list();

            for(Order order : orders) {
                freeReservation(order.getId());
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to clear all reservations: " + e.getMessage());
        }
    }

    public Order confirmOrder(int id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
            try {
                session.beginTransaction();
                Order order = session.get(Order.class, id);

                if(order != null && order.getStatus().equals(Order.WAITING)) {
                    order.setStatus(Order.CONFIRMED);
                    session.update(order);

                    session.getTransaction().commit();

                    return order;
                } else {
                    throw new EntityNotFoundException("Order isn't in state WAITING or doesn't exist.");
                }
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new HibernateException("Could not confirm order: " + e.getMessage());
            }
    }
}

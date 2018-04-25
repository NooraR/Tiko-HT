package database;

import datamodel.*;
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
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();
            Order order = session.get(Order.class, id);

            session.getTransaction().commit();

            return order;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception("Failed to get order by id: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public Order createReservation(User user, List<Work> works) throws HibernateException {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();
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

            for (Work work : works) {
                for (int i = 0; i < work.getAmount(); i++) {
                    //Get an available product
                    Query query = session.createQuery("from Product WHERE work=:work AND status = :status");
                    query.setParameter("work", work);
                    query.setParameter("status", Product.FREE);

                    Product product = (Product) query.setMaxResults(1).uniqueResult();
                    if (product == null) {
                        throw new EntityNotFoundException("No products available for work");
                    }
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
            }, 30 * 1000 * 60);

            //Send changes
            session.getTransaction().commit();

            return order;
        }catch (EntityNotFoundException e) {
            session.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to create reservation: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private double calculatePostage(List<Product> products) {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Postage ORDER BY weight ASC");
            List<Postage> postageFees = (List<Postage>) query.list();

            session.getTransaction().commit();

            double totalWeight = 0;
            for (Product product : products) {
                totalWeight += product.getWork().getWeight();
            }
            double totalFee = 0;

            while (totalWeight > 0) {
                //Get appropriate fee
                int i = 0;
                while (i + 1 < postageFees.size() && postageFees.get(i).getWeight() <= totalWeight) {
                    i++;
                }
                //Change weight and total fee accordingly
                totalFee += postageFees.get(i).getFee();
                totalWeight -= postageFees.get(i).getWeight();
            }

            return totalFee;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new HibernateException("Failed to get postage data");
        }
    }

    public void freeReservation(int id) throws HibernateException {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

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
        } finally {
            session.close();
        }
    }

    public void freeAllReservations() {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();

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
        } finally {
            session.close();
        }
    }

    public Order confirmOrder(int id) throws HibernateException {
        Session session = sessionFactory.withOptions().tenantIdentifier("central").openSession();
            try {
                session.beginTransaction();
                Order order = session.get(Order.class, id);

                if(order != null && order.getStatus().equals(Order.WAITING)) {
                    order.setStatus(Order.CONFIRMED);

                    for(Product product : order.getProducts()) {
                        product.setStatus("UNAVAILABLE");
                        session.update(product);
                    }

                    session.update(order);

                    session.getTransaction().commit();

                    return order;
                } else {
                    throw new EntityNotFoundException("Order isn't in state WAITING or doesn't exist.");
                }
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new HibernateException("Could not confirm order: " + e.getMessage());
            } finally {
                session.close();
            }
    }
}

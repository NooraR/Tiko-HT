package datamodel;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;


@Entity
@Table(name = "userorder", schema = "central", catalog = "tikoht")
public class Order {

    @Id
    @SequenceGenerator(name = "order_id_seq", sequenceName = "userorder_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Basic
    @Column(name = "orderdate", nullable = false)
    private Date orderDate;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'WAITING' NOT NULL")
    private String status;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User orderer;

    @OneToMany
    @JoinColumn(name = "orderid", referencedColumnName = "id")
    private List<Product> products;

    @Transient
    private Timer timer;

    public Order(){

        this.id = -1;
        this.orderDate = null;
        this.status = null;
        products = null;
        timer = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getOrderer() {
        return orderer;
    }

    public void setOrderer(User orderer) {
        this.orderer = orderer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderDate);
    }
}

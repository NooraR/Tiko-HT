package datamodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


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
    private Date orderdate;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "order_status DEFAULT 'WAITING' NOT NULL")
    @Enumerated(EnumType.STRING)
    private order_status status;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User orderer;

    @OneToMany
    @JoinColumn(name = "orderid", referencedColumnName = "id")
    private List<Product> products;

    private enum order_status {
        WAITING, PROCESSED, COMPLETE
    }

    public Order(){

        this.id = -1;
        this.orderdate = null;
        this.status = null;
        products = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public order_status getStatus() {
        return status;
    }

    public void setStatus(order_status status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(orderdate, order.orderdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderdate);
    }
}
